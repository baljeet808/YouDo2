package common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Settings
import domain.models.MenuItem
import org.jetbrains.compose.resources.DrawableResource
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.app_icon
import youdo2.composeapp.generated.resources.chat_illustration
import youdo2.composeapp.generated.resources.set_reminders
import youdo2.composeapp.generated.resources.todo_illustration


const val DestinationSettingsRoute = "settings"
const val DestinationProjectsRoute = "projects"

const val ConstFirstScreenDescription = "Work on tasks with family and friends. and finish them all together."
const val ConstSecScreenDescription = "Chat about specific task. Manage permissions for collaborators."
const val ConstThirdScreenDescription = "Create multiple DoToos and track there progress."
const val ConstSampleAvatarUrl = "https://firebasestorage.googleapis.com/v0/b/youdotoo-81372.appspot.com/o/20.png?alt=media&token=fa1489d4-8951-4ef6-8f96-862938aedb62"



const val maxTitleCharsAllowed = 60
const val maxDescriptionCharsAllowed = 120
const val maxTitleCharsAllowedForProject = 40



const val InvitationPending = 0
const val InvitationAccepted = 1
const val InvitationDeclined = 2
const val InvitationArchived = 3

const val AccessTypeAdmin = 0
const val AccessTypeEditor = 1
const val AccessTypeViewer = 2

val menuItems = arrayListOf(
    MenuItem(
        id = DestinationProjectsRoute,
        title = "Projects",
        icon = Icons.Outlined.MoreVert,
        contentDescription = "Menu item to show all Projects"
    ),
    MenuItem(
        id = DestinationSettingsRoute,
        title = "Settings",
        icon = Icons.Outlined.Settings,
        contentDescription = "Menu item to show settings"
    )
)



data class OnBoardPagerContent(
    val title: String,
    val res: DrawableResource,
    val description: String
)


fun getOnBoardPagerContentList () : List<OnBoardPagerContent>{
    return listOf(
        OnBoardPagerContent(
            title = "Tag Tasks Directly into Your Chat!",
            res = Res.drawable.chat_illustration,
            description = ConstSecScreenDescription
        ),
        OnBoardPagerContent(
            title = "Organize Tasks Easily!",
            res = Res.drawable.set_reminders,
            description = ConstThirdScreenDescription
        ),
        OnBoardPagerContent(
            title = "To it together!",
            res = Res.drawable.todo_illustration,
            description = ConstFirstScreenDescription
        )
    )
}