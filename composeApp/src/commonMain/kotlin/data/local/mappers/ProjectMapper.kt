package data.local.mappers

import data.local.entities.ProjectEntity
import domain.models.Project


fun Project.toProjectEntity() : ProjectEntity {
    return ProjectEntity(
        id = id,
        name = name,
        description = description,
        ownerId = ownerId,
        update = update,
        color = color,
        collaboratorIds = collaboratorIds.joinToString ( "," ),
        viewerIds = viewerIds.joinToString (","),
        updatedAt = updatedAt,
        category = category,
        photoUrl = photoUrl,
        extraUrl = extraUrl,
        projectLikeIds = projectLikeIds.joinToString ( "," ),
    )
}


fun ProjectEntity.toProject(): Project{
    return Project(
        id = id,
        name = name,
        description = description,
        ownerId = ownerId,
        collaboratorIds = if(collaboratorIds.isNotBlank()){
            collaboratorIds.split(",")
        }else{
            listOf()
        },
        viewerIds = if(viewerIds.isNotBlank()){
            viewerIds.split(",")
        }else{
            listOf()
        },
        update = update,
        color = color,
        updatedAt = updatedAt,
        category = category,
        photoUrl = photoUrl,
        extraUrl = extraUrl,
        projectLikeIds = if(projectLikeIds.isNotBlank()) {
            projectLikeIds.split(",")
        }else{
            listOf()
        }
    )
}