import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.local.mappers.toMessageEntity
import data.local.mappers.toProjectEntity
import data.local.mappers.toTaskEntity
import data.local.mappers.toUserEntity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.models.Message
import domain.models.Project
import domain.models.Task
import domain.repository_interfaces.DataStoreRepository
import domain.use_cases.message_use_cases.DeleteMessageUseCase
import domain.use_cases.message_use_cases.GetAllMessagesByProjectIDUseCase
import domain.use_cases.message_use_cases.UpsertMessagesUseCase
import domain.use_cases.project_use_cases.DeleteProjectUseCase
import domain.use_cases.project_use_cases.GetProjectsUseCase
import domain.use_cases.project_use_cases.UpsertProjectUseCase
import domain.use_cases.task_use_cases.DeleteTaskUseCase
import domain.use_cases.task_use_cases.GetProjectTasksUseCase
import domain.use_cases.task_use_cases.UpsertTasksUseCase
import domain.use_cases.user_use_cases.GetUserByIdUseCase
import domain.use_cases.user_use_cases.UpsertUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class AppViewModel(
    private val upsertProjectUseCase: UpsertProjectUseCase,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val getProjectTasksUseCase: GetProjectTasksUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val upsertTasksUseCase: UpsertTasksUseCase,
    private val getAllMessagesByProjectIDUseCase: GetAllMessagesByProjectIDUseCase,
    private val deleteMessageUseCase: DeleteMessageUseCase,
    private val upsertUserUseCase: UpsertUserUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val upsertMessagesUseCase: UpsertMessagesUseCase
) : ViewModel(), KoinComponent {

    private val dataStoreRepository : DataStoreRepository by inject<DataStoreRepository>()

    var userState by mutableStateOf(AppUIState())
        private set

    init {
        checkUserLoggedInStatus()
    }

    private fun checkOnboardingStatus(){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.hasOnboardedAsFlow().collect{
                withContext(Dispatchers.Main) {
                    userState = userState.copy(hasOnboarded = it, resultFound = true)
                }
            }
        }
    }

    private fun checkUserLoggedInStatus(){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.isUserLoggedInAsFlow().collect {
                if(it){
                    withContext(Dispatchers.Main) {
                        userState = userState.copy(isUserLoggedIn = true, resultFound = true)
                    }
                    fetchUserId()
                }else{
                   //checkOnboardingStatus()
                    withContext(Dispatchers.Main) {
                        userState = userState.copy(isUserLoggedIn = false, resultFound = true)
                    }
                }
            }
        }
    }

    private fun fetchUserId() = viewModelScope.launch(Dispatchers.IO){
        dataStoreRepository.userIdAsFlow().collect {
            withContext(Dispatchers.Main) {
                userState = userState.copy(userId = it)
            }
            syncDataFromFirebase(it)
        }
    }

    private fun syncDataFromFirebase(userId : String) = viewModelScope.launch(Dispatchers.IO){
        try {
            Firebase.firestore.collection("projects")
                .where { "ownerId" equalTo userId }
                .snapshots.collect{ projectsQuerySnapshot ->
                    val projects = projectsQuerySnapshot.documents.map { documentSnapshot ->
                        documentSnapshot.data<Project>()
                    }
                    updateProjectsLocally(projects)
                    for (project in projects){
                        Firebase.firestore.document("projects/${project.id}")
                            .collection("tasks")
                            .snapshots.collect{ tasksQuerySnapshot ->
                                val tasks = tasksQuerySnapshot.documents.map { documentSnapshot ->
                                    documentSnapshot.data<Task>()
                                }
                                updateTasksLocally(tasks = tasks, project = project)
                            }

                        Firebase.firestore.document("projects/${project.id}")
                            .collection("messages")
                            .snapshots.collect{ messagesQuerySnapshot ->
                                val messages = messagesQuerySnapshot.documents.map { documentSnapshot ->
                                    documentSnapshot.data<Message>()
                                }
                                updateMessagesLocally(messages, project = project)
                            }
                        updateUsersLocally(project)
                    }
                }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun updateUsersLocally(project : Project) = viewModelScope.launch(Dispatchers.IO){
        val combineCollaboratorsAndViewersIds = project.collaboratorIds + project.viewerIds
        combineCollaboratorsAndViewersIds.forEach { userId ->
            val userEntity = getUserByIdUseCase(userId)
            if(userEntity == null){
                val userSnapShot = Firebase.firestore.collection("users").document(userId).get()
                val user = userSnapShot.data<domain.models.User>()
                upsertUserUseCase(listOf(user.toUserEntity()))
            }
        }
    }


    private fun updateMessagesLocally(messages : List<Message>, project : Project) = viewModelScope.launch(Dispatchers.IO){
        val localMessages = getAllMessagesByProjectIDUseCase(projectId = project.id)
        val messagesToDelete = localMessages.filter { localMessage ->
            messages.none { message ->
                message.id == localMessage.id
            }
        }
        messagesToDelete.forEach {
            deleteMessageUseCase(it)
        }
        upsertMessagesUseCase(messages.map { it.toMessageEntity() })
    }

    private fun updateTasksLocally(tasks : List<Task>, project : Project) = viewModelScope.launch(Dispatchers.IO){
        val localTasks = getProjectTasksUseCase(projectId = project.id)
        val tasksToDelete = localTasks.filter { localTask ->
            tasks.none { task ->
                task.id == localTask.id
            }
        }
        tasksToDelete.forEach {
            deleteTaskUseCase(it)
        }
        upsertTasksUseCase(tasks.map { it.toTaskEntity() })
    }

    private fun updateProjectsLocally(projects : List<Project>) = viewModelScope.launch(Dispatchers.IO){
        val localProjects = getProjectsUseCase()
        val projectsToDelete = localProjects.filter { localProject ->
            projects.none { project ->
                project.id == localProject.id
            }
        }
        projectsToDelete.forEach {
            deleteProjectUseCase(it)
        }
        upsertProjectUseCase(projects.map { it.toProjectEntity() })
    }

}

data class AppUIState(
    val isUserLoggedIn : Boolean = false,
    val hasOnboarded : Boolean = false,
    val resultFound : Boolean = false,
    val userId : String = ""
)
