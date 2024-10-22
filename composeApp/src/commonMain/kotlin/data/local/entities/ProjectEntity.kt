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
    var ownerName : String = "",
    var ownerId : String = "",
    var update : String = "",
    var color : String = getRandomColor().name,
    var collaboratorIds : String = "",
    var viewerIds: String = "",
    var updatedAt : Long = 0L,
    var hideFromDashboard : Boolean = false,
    var category : String = "",
    var photoUrl : String = "",
    var extraUrl : String = "",
    var projectLikeIds : String = "",
)