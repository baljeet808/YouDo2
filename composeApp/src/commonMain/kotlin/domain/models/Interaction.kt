package domain.models

import kotlinx.serialization.Serializable


@Serializable
data class Interaction(
    val userId : String,
    val emoticonName : String
)
