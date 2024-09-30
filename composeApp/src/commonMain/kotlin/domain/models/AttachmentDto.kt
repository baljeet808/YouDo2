package domain.models

import coil3.Uri

data class AttachmentDto(
    val uri : Uri,
    val mimeType : String,
    var uploadingFailed : Boolean = false
)
