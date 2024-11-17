package presentation.chat

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import data.local.entities.MessageEntity
import data.local.entities.UserEntity
import domain.models.Project
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI


const val DestinationMessageRoute = "messages/{projectId}/{userId}"

@ExperimentalResourceApi
@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.addChatViewDestination(navController: NavController){
    composable(
        route = DestinationMessageRoute,
        arguments = listOf(
            navArgument("projectId"){
                type = NavType.StringType
            },
            navArgument("userId"){
                type = NavType.StringType
            },
        )
    ){

        val viewModel = koinViewModel<ChatViewModel>()

       // val lazyPagedMessages : List<MessageEntity> = viewModel.getAllMessagesOfThisProject().collectAsLazyPagingItems()

      /*  val project by viewModel.getProjectById().collectAsState(initial = null)

        val participants : List<UserEntity> = project?.let {
            viewModel.getUsersOFProject(it.toProject()).collectAsState(initial = listOf())
        }?.value?: listOf()
*/


        ChatView(
            participants = emptyList<UserEntity>(),
            messages = emptyList<MessageEntity>(),
            showAttachment = {
                navController.navigate(  "attachment_viewer/".plus(it.id))
            },
            interactOnMessage = { message, emoticon ->
                viewModel.interactWithMessage(
                    message= message,
                    emoticon = emoticon
                )
            },
            onClose={
                navController.popBackStack()
            },
            project = Project()
        )
    }
}