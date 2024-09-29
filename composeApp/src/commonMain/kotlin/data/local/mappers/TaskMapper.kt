package data.local.mappers

import data.local.entities.TaskEntity
import domain.models.Task


fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        projectId = projectId,
        description = description,
        dueDate = dueDate,
        createDate = createDate,
        done = done,
        priority = priority,
        updatedBy = updatedBy
    )
}

fun TaskEntity.toTask(): Task{
    return Task(
        id = id,
        title = title,
        projectId = projectId,
        description = description,
        dueDate = dueDate,
        createDate = createDate,
        done = done,
        priority = priority,
        updatedBy = updatedBy
    )
}