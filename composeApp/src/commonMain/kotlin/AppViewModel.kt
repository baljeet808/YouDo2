
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.local.mappers.toMessageEntity
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
    private val getProjectTasksUseCase: GetProjectTasksUseCase,
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
        userState = userState.copy(loading = true)
        //checkOnboardingStatus()
        checkUserLoggedInStatus()
    }

    private fun checkOnboardingStatus(){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.hasOnboardedAsFlow().collect{ hasOnBoarded ->
                if(hasOnBoarded){
                    checkUserLoggedInStatus()
                }else{
                    withContext(Dispatchers.Main) {
                        userState = userState.copy(
                            hasOnboarded = false,
                            isUserLoggedIn = false,
                            loading = false
                        )
                    }
                }
            }
        }
    }

    private fun checkUserLoggedInStatus(){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.isUserLoggedInAsFlow().collect {
                if(it){
                    withContext(Dispatchers.Main) {
                        userState = userState.copy(
                            hasOnboarded = true,
                            isUserLoggedIn = true,
                            loading = false
                        )
                    }
                    fetchUserId()
                }else{
                   checkOnboardingStatus()
                    withContext(Dispatchers.Main) {
                        userState = userState.copy(
                            hasOnboarded = true,
                            isUserLoggedIn = false,
                            loading = false
                        )
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
        }
    }

    fun syncDataFromFirebase(userId : String) = viewModelScope.launch(Dispatchers.IO){
        try {
            Firebase.firestore.collection("projects")
                .where { "ownerId" equalTo userId }
                .snapshots.collect{ projectsQuerySnapshot ->
                    val projects = projectsQuerySnapshot.documents.map { documentSnapshot ->
                        documentSnapshot.data<Project>()
                    }
                    projects.forEach { project ->
                        launch { syncTasksForProject(project) }
                        launch { syncMessagesForProject(project) }
                        launch { updateUsersLocally(project) }
                    }
                }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private suspend fun syncTasksForProject(project: Project) {
        try {
            Firebase.firestore.collection("projects")
                .document(project.id)
                .collection("tasks")
                .snapshots.collect{ tasksQuerySnapshot ->
                    val tasks = tasksQuerySnapshot.documents.map { documentSnapshot ->
                        documentSnapshot.data<Task>()
                    }
                    updateTasksLocally(tasks = tasks, project = project)
                }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private suspend fun syncMessagesForProject(project: Project) {
        try {
        Firebase.firestore.collection("projects")
            .document(project.id)
            .collection("messages")
            .snapshots.collect{ messagesQuerySnapshot ->
                val messages = messagesQuerySnapshot.documents.map { documentSnapshot ->
                    documentSnapshot.data<Message>()
                }
                updateMessagesLocally(messages, project = project)
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private suspend fun updateUsersLocally(project : Project){
        try {
            val combineCollaboratorsAndViewersIds = project.collaboratorIds + project.viewerIds
            combineCollaboratorsAndViewersIds.forEach { userId ->
                val userEntity = getUserByIdUseCase(userId)
                if(userEntity == null){
                    val userSnapShot = Firebase.firestore.collection("users").document(userId).get()
                    val user = userSnapShot.data<domain.models.User>()
                    upsertUserUseCase(listOf(user.toUserEntity()))
                }
            }
        }catch (e : Exception){
            e.printStackTrace()
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


}

data class AppUIState(
    val isUserLoggedIn : Boolean = false,
    val hasOnboarded : Boolean = false,
    val userId : String = "",
    val loading : Boolean = true,
)
