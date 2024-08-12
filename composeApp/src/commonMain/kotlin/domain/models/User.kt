package domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id : String,
    var name : String,
    var email :String,
    var joined : Long,
    var avatarUrl: String = "",
    var firebaseToken : String = ""
)
