package presentation.chat.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.getRandomId
import common.getSampleDateInLong
import data.local.entities.MessageEntity
import data.local.mappers.toMessage
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.models.AttachmentDto
import domain.models.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class MessageBoxViewModel : ViewModel(), KoinComponent {

    private var chatRef = Firebase.firestore.collection("projects")


    fun sendMessage(
        messageString: String,
        isUpdate: Boolean = false,
        updateMessage: String = "",
        attachments: List<AttachmentDto> = emptyList(),
        project : Project
    ) = viewModelScope.launch(Dispatchers.IO) {

        if(updateMessage.isNotEmpty() || messageString.isNotEmpty()) {
            val newMessageID = getRandomId()
            val newMessage = MessageEntity(
                id = newMessageID,
                message = if (isUpdate) {
                    updateMessage
                } else {
                    messageString
                },
                senderId = "",
                createdAt = getSampleDateInLong(),
                isUpdate = isUpdate,
                attachmentUrl = null,
                attachmentName = null,
                interactions = "",
                projectId = project.id
            )
            chatRef
                .document(project.id)
                .collection("messages")
                .document(newMessageID)
                .set(newMessage.toMessage())
        }
    }

}