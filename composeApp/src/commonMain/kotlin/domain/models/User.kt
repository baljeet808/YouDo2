package domain.models

import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id : String = "12345",
    var name : String = "Who Knows 🤪",
    var email :String = "SomeEmail@YouDoToo.com",
    var joined : Long = Clock.System.now().toEpochMilliseconds(),
    var avatarUrl: String = "",
    var firebaseToken : String = "",
    var sharingCode : String = "",
)
