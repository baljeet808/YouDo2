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
    "https://firebasestorage.googleapis.com/v0/b/youdo2.appspot.com/o/Alien1.png?alt=media&token=1746e23a-5c21-40f9-b3da-d5b1e1d6e777",
    "https://firebasestorage.googleapis.com/v0/b/youdo2.appspot.com/o/Kukku.png?alt=media&token=e6294874-1044-47cf-ba1c-fbb6cd88802e",
    "https://firebasestorage.googleapis.com/v0/b/youdo2.appspot.com/o/Alien8.png?alt=media&token=dca1009d-19db-4bee-8877-d70173f5ad2f",
    "https://firebasestorage.googleapis.com/v0/b/youdo2.appspot.com/o/Alien7.png?alt=media&token=02f052c2-54c8-4f8b-9cf8-b5250c099359",
    "https://firebasestorage.googleapis.com/v0/b/youdo2.appspot.com/o/Alien6.png?alt=media&token=e7741e0a-4f78-4da5-bc46-af22115d732c",
    "https://firebasestorage.googleapis.com/v0/b/youdo2.appspot.com/o/Alien5.png?alt=media&token=73067112-601c-43f7-bdea-eff17e7fa788",
    "https://firebasestorage.googleapis.com/v0/b/youdo2.appspot.com/o/Alien4.png?alt=media&token=2ff3741a-8463-41ba-bb08-32d3d7cbc48b",
    "https://firebasestorage.googleapis.com/v0/b/youdo2.appspot.com/o/Alien3.png?alt=media&token=e83444d5-2e5b-46e7-b60a-b091b8259b55",
    "https://firebasestorage.googleapis.com/v0/b/youdo2.appspot.com/o/Alien2.png?alt=media&token=9315b56f-0c79-449e-843f-ca6d45052f33",
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
