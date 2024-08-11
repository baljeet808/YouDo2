package common

import data.local.entities.ProjectEntity


enum class EnumRoles {
    ProAdmin, Admin, Editor, Viewer, Blocked
}



fun getRole(project: ProjectEntity): EnumRoles {
    val currentUserId = ""
    return if (project.ownerId == currentUserId) {
        /*if (SharedPref.isUserAPro) {
            EnumRoles.ProAdmin
        } else {
            EnumRoles.Admin
        }*/
        EnumRoles.Viewer
    } else if (project.collaboratorIds.contains(currentUserId)) {
        EnumRoles.Editor
    } else if (project.viewerIds.contains(currentUserId)) {
        EnumRoles.Viewer
    } else {
        EnumRoles.Blocked
    }
}


fun doesUserHavePermissionToEdit(project: ProjectEntity) : Boolean{
    val currentUserId = ""
    return  (project.ownerId == currentUserId) || project.collaboratorIds.contains(currentUserId)
}


