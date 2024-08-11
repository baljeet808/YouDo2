/*

fun createDummyProject(newProjectId: String) {
    val newProject = ProjectEntity(
        id = newProjectId,
        name = "My tasks",
        description = "This is auto generated project, you can modify this project as per your wish.",
        ownerId = uiState.userId,
        collaboratorIds = "",
        viewerIds = "",
        update = "${uiState.userName} created this project named 'My tasks'.",
        color = getRandomColor(),
        updatedAt = getSampleDateInLong()
    )
    createProject(newProject)
}

private fun createProject(project : ProjectEntity){
    //TODO: check if user is pro or not
    if(true){
        createProjectOnServer(project)
    }else{
        createProjectLocally(project)
    }
}

private fun createProjectOnServer(project: ProjectEntity) {
    viewModelScope.launch(Dispatchers.IO) {
        projectsReference.document(project.id)
            .set(project)
    }
}

private fun createProjectLocally(project: ProjectEntity) {
    viewModelScope.launch(Dispatchers.IO) {
        upsertProjectUseCase(listOf(project))
    }
}*/
