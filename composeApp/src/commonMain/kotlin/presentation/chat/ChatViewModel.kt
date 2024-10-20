package presentation.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.local.entities.MessageEntity
import data.local.entities.ProjectEntity
import data.local.mappers.toMessage
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.models.Project
import domain.use_cases.message_use_cases.GetPagedMessagesOfProject
import domain.use_cases.project_use_cases.GetProjectByIdAsFlowUseCase
import domain.use_cases.user_use_cases.GetUsersByIdsUseCase
import kotlinx.coroutines.flow.Flow


class ChatViewModel(
    private val getProjectByIdAsFlowUseCase: GetProjectByIdAsFlowUseCase,
    private val getUsersByIdsUseCase: GetUsersByIdsUseCase,
    private val getPagedMessagesOfProject: GetPagedMessagesOfProject
) : ViewModel() {


    private var chatRef = Firebase.
        firestore.collection("projects")


   /* fun getProjectById(): Flow<ProjectEntity> = getProjectByIdAsFlowUseCase(projectId)


    fun getUsersOFProject( project : Project) = getUsersByIdsUseCase(project.getUserIds())



    fun getAllMessagesOfThisProject() : Flow<PagingData<MessageEntity>> = getPagedMessagesOfProject(projectId)
        .cachedIn(viewModelScope)

*/
    fun interactWithMessage(
        message: MessageEntity,
        emoticon: String
    ) {
        val newMessage = message.copy()
       /* newMessage.updateInteraction(emoticon)
        chatRef
            .document(projectId)
            .collection("messages")
            .document(message.id)
            .set(newMessage.toMessage())*/
    }
}