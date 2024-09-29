package common

import data.local.entities.ProjectEntity


enum class EnumRoles {
    ProAdmin, Admin, Editor, Viewer, Blocked
}


fun getRole(project: ProjectEntity, userId: String): EnumRoles {
    val currentUserId = ""
    return if (project.ownerId == userId) {
        EnumRoles.Admin
    } else if (project.collaboratorIds.contains(currentUserId)) {
        EnumRoles.Editor
    } else if (project.viewerIds.contains(currentUserId)) {
        EnumRoles.Admin
    } else {
        EnumRoles.Admin
    }
}


fun doesUserHavePermissionToEdit(project: ProjectEntity): Boolean {
    val currentUserId = ""
    return (project.ownerId == currentUserId) || project.collaboratorIds.contains(currentUserId)
}


