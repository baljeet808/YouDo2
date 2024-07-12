package data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import common.getRandomColor


@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey
    var id : String = "",
    var name : String = "",
    var description : String = "",
    var ownerId : String = "",
    var update : String = "",
    var color : String = getRandomColor(),
    var collaboratorIds : String = "",
    var viewerIds: String = "",
    var updatedAt : Long = 0L,
    var hideFromDashboard : Boolean = false
)