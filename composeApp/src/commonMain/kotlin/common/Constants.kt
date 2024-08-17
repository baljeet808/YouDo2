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
const val ConstSecScreenDescription = "Tame your to-do list with a swipe. Organize, prioritize, and crush your goals like a productivity ninja!"
const val ConstThirdScreenDescription = "Set it and forget it! With smart reminders, you’ll be on time, every time—no more last-minute scrambles."

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
            backgroundColor = EnumProjectColors.Murrey.getColor()
        )
    )
}

val avatars = arrayListOf(
    "https://firebasestorage.googleapis.com/v0/b/youdotoo-81372.appspot.com/o/20.png?alt=media&token=fa1489d4-8951-4ef6-8f96-862938aedb62",
    "https://firebasestorage.googleapis.com/v0/b/youdotoo-81372.appspot.com/o/1.png?alt=media&token=b15d14e9-722d-410d-b9b4-23682b5773f3",
    "https://firebasestorage.googleapis.com/v0/b/youdotoo-81372.appspot.com/o/2.png?alt=media&token=68e95bc9-9553-4027-90d9-af688b9fd0f4",
    "https://firebasestorage.googleapis.com/v0/b/youdotoo-81372.appspot.com/o/16.png?alt=media&token=160bd284-4b6a-488c-b66b-b421ebde9c21",
    "https://firebasestorage.googleapis.com/v0/b/youdotoo-81372.appspot.com/o/4.png?alt=media&token=3c06a69a-7f00-4238-b6cf-3296f3532576",
    "https://firebasestorage.googleapis.com/v0/b/youdotoo-81372.appspot.com/o/6.png?alt=media&token=3fefb778-d6a4-4c05-9d0b-ed8dde6091e5",
    "https://firebasestorage.googleapis.com/v0/b/youdotoo-81372.appspot.com/o/9.png?alt=media&token=dd24b271-e068-43a6-b0f4-c3142873575c"
)

val loginHeadings = arrayOf(
    "Time to join the Party!",
    "Welcome Back, Superstar!",
    "Ready to Rock?",
    "Welcome to the Fun Zone!",
    "Come On In, We Missed You!"
)

val passwordPlaceholders = arrayOf(
    "Shh... It’s a secret",
    "Super secret password",
    "Magic words here",
    "Don’t tell anyone!",
    "The key to the kingdom"
)

val signupHeadings = arrayOf(
    "Join the inner Circle",
    "Step into the Spotlight",
    "Sign Up & Stand Out",
    "Claim Your Spot",
    "Be Part of the Crew",
    "Sign Up & Sparkle",
    "Join the Elite Squad",
    "Enter the Cool Zone",
    "Be One of the Greats"
)
