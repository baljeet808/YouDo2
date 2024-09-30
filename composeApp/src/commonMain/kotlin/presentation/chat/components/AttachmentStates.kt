package presentation.chat.components

import domain.models.AttachmentDto

sealed class AttachmentStates{
        object Empty : AttachmentStates()
        object Sending : AttachmentStates()
        object Sent : AttachmentStates()
        data class CompletedWithErrors(val failedAttachments : ArrayList<AttachmentDto>) : AttachmentStates()
    }