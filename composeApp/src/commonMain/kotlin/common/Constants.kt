package common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.Color
import domain.models.MenuItem
import org.jetbrains.compose.resources.DrawableResource
import presentation.projects.helpers.DESTINATION_PROJECTS_ROUTE
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.chat_illustration
import youdo2.composeapp.generated.resources.set_reminders
import youdo2.composeapp.generated.resources.todo_illustration



const val ConstFirstScreenDescription = "From coffee breaks to commutes, your tasks travel with you. Sync up and check off from any device—anywhere, anytime."
const val ConstSecScreenDescription = "Tame your to-do list with a swipe. Organize, prioritize, and crush your goals like a productivity ninja! 🥷"
const val ConstThirdScreenDescription = "Set it and forget it! With smart reminders, you’ll be on time, every time—no more last-minute scrambles.🍳"

const val maxTitleCharsAllowed = 60
const val maxDescriptionCharsAllowed = 120
const val maxTitleCharsAllowedForProject = 40


val menuItems = arrayListOf(
    MenuItem(
        id = DESTINATION_PROJECTS_ROUTE,
        title = "Projects",
        icon = Icons.Outlined.MoreVert,
        contentDescription = "Menu item to show all Projects"
    ),
    MenuItem(
        id = "",
        title = "Settings",
        icon = Icons.Outlined.Settings,
        contentDescription = "Menu item to show settings"
    )
)



data class OnBoardPagerContent(
    val title: String,
    val res: DrawableResource,
    val description: String,
    val backgroundColor : Color = Color.Black
)


fun getOnboardingPagerContentList () : List<OnBoardPagerContent>{
    return listOf(
        OnBoardPagerContent(
            title = "Conquer Your \nChaos",
            res = Res.drawable.chat_illustration,
            description = ConstSecScreenDescription,
            backgroundColor = EnumProjectColors.Black.getColor()
        ),
        OnBoardPagerContent(
            title = "Deadlines? \nHandled!",
            res = Res.drawable.set_reminders,
            description = ConstThirdScreenDescription,
            backgroundColor = EnumProjectColors.Indigo.getColor()
        ),
        OnBoardPagerContent(
            title = "All Your Stuff,\nEverywhere",
            res = Res.drawable.todo_illustration,
            description = ConstFirstScreenDescription,
            backgroundColor = EnumProjectColors.RoseRed.getColor()
        )
    )
}