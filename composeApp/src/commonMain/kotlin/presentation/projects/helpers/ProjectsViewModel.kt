package presentation.projects.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.EnumRoles
import common.getRole
import common.getSampleDateInLong
import data.local.entities.ProjectEntity
import data.local.entities.TaskEntity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import domain.use_cases.project_use_cases.GetProjectByIdUseCase
import domain.use_cases.project_use_cases.GetProjectsWithDoToosUseCase
import domain.use_cases.project_use_cases.UpsertProjectUseCase
import domain.use_cases.task_use_cases.DeleteTaskUseCase
import domain.use_cases.task_use_cases.UpsertTasksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent


class ProjectsViewModel(
    private val getProjectsWithDoToosUseCase: GetProjectsWithDoToosUseCase,
    private val getProjectByIdUseCase: GetProjectByIdUseCase,
    private val upsertProjectUseCase: UpsertProjectUseCase,
    private val upsertTasksUseCase: UpsertTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel(), KoinComponent {

    private var projectsReference = Firebase.firestore.collection("projects")

    private var user = Firebase.auth.currentUser

    var uiState by mutableStateOf(
        ProjectsUIState(
            userName = user?.displayName?:"",
            userId = user?.uid?:"",
            userEmail = user?.email?:""
        )
    )
        private set


    init {
        projectsWithTaskCount()
    }

    /**
     * Fetch all projects and there tasks from local db
     * Valid rule for both pro and non pro members
     * **/
    private fun projectsWithTaskCount() {
        viewModelScope.launch(Dispatchers.IO) {
            getProjectsWithDoToosUseCase().collect {
                withContext(Dispatchers.Main){
                    uiState = uiState.copy(projects = it)
                }
            }
        }
    }


    fun updateTask(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val projectEntity = getProjectByIdUseCase(projectId = task.projectId)
            updateTask(task,projectEntity)
        }
    }
    private fun updateTask(task: TaskEntity, project : ProjectEntity){
        when(getRole(project, userId =  uiState.userId)){
            EnumRoles.ProAdmin -> {
                updateTaskOnServer(task, project)
            }
            EnumRoles.Admin -> {
                updateTaskLocally(task, project)
            }
            EnumRoles.Editor -> {
                updateTaskOnServer(task, project)
            }
            EnumRoles.Viewer -> {
                //Do nothing can't update anything
                //UI handles this by itself
            }
            EnumRoles.Blocked -> {
                //Do nothing can't update anything
                //UI handles this by itself
            }
        }
    }

    private fun updateTaskOnServer(task : TaskEntity, project: ProjectEntity){
        viewModelScope.launch(Dispatchers.IO) {
            projectsReference
                .document(task.projectId)
                .collection("todos")
                .document(task.id)
                .set(task)
        }
    }

    private fun updateTaskLocally(task : TaskEntity, project: ProjectEntity){
        viewModelScope.launch(Dispatchers.IO) {
            upsertTasksUseCase(listOf(task))
            updateProject(project)
        }
    }


    fun deleteTask(task : TaskEntity){
        viewModelScope.launch(Dispatchers.IO) {
            val project = getProjectByIdUseCase(projectId = task.projectId)
            deleteTask(task, project)
        }
    }


    private fun deleteTask(task : TaskEntity, projectEntity: ProjectEntity){
        val project = projectEntity
        when(getRole(project, userId =  uiState.userId)){
            EnumRoles.ProAdmin -> {
                deleteTaskOnServer(task, project)
            }
            EnumRoles.Admin -> {
                deleteTaskLocally(task, project)
            }
            EnumRoles.Editor -> {
                deleteTaskOnServer(task, project)
            }
            EnumRoles.Viewer -> {
                //Do nothing can't update anything
                //UI handles this by itself
            }
            EnumRoles.Blocked -> {
                //Do nothing can't update anything
                //UI handles this by itself
            }
        }
    }
    private fun deleteTaskOnServer(task: TaskEntity, project: ProjectEntity){
        viewModelScope.launch(Dispatchers.IO) {
            projectsReference
                .document(task.projectId)
                .collection("todos")
                .document(task.id)
                .delete()
        }
    }

    private fun deleteTaskLocally(task: TaskEntity, project: ProjectEntity){
        viewModelScope.launch(Dispatchers.IO) {
            deleteTaskUseCase(task)
            updateProject(project)
        }
    }


    fun hideOrShowProjectTasksOnDashboard(project : ProjectEntity){
        val projectCopy = project.copy()
        projectCopy.hideFromDashboard = project.hideFromDashboard.not()
        updateProjectLocally(projectCopy)
    }

    private fun updateProject(project : ProjectEntity){
        val projectCopy = project.copy()
        projectCopy.updatedAt = getSampleDateInLong()

        when(getRole(project, userId =  uiState.userId)){
            EnumRoles.ProAdmin -> {
                updateProjectOnSever(projectCopy)
            }
            EnumRoles.Admin -> {
                updateProjectLocally(projectCopy)
            }
            EnumRoles.Editor -> {
                updateProjectOnSever(projectCopy)
            }
            EnumRoles.Viewer -> {
                //Do nothing can't update anything
                //UI handles this by itself
            }
            EnumRoles.Blocked -> {
                //Do nothing can't update anything
                //UI handles this by itself
            }
        }
    }


    private fun updateProjectOnSever(project : ProjectEntity){
        viewModelScope.launch(Dispatchers.IO) {
            projectsReference
                .document(project.id)
                .set(project)
        }
    }

    private fun updateProjectLocally(project : ProjectEntity){
        viewModelScope.launch(Dispatchers.IO) {
            upsertProjectUseCase(listOf(project))
        }
    }

}