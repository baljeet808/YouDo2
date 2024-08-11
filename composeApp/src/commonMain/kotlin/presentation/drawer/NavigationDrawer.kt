package presentation.drawer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.menuItems
import domain.models.MenuItem
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.shared.fonts.RobotoFontFamily
import presentation.theme.LessTransparentWhiteColor
import presentation.theme.NightDotooBrightPink
import presentation.theme.NightTransparentWhiteColor
import presentation.theme.getLightThemeColor
import presentation.theme.getNightDarkColor
import presentation.theme.getNightLightColor
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.app_icon


/**
 * Updated by Baljeet singh.
 * **/
@Composable
fun NavigationDrawer(
    userEmail : String = "",
    userName : String = "",
    menuItems: List<MenuItem>,
    onMenuItemClick: (MenuItem) -> Unit,
    closeDrawer : () -> Unit,
    logout: () -> Unit,
    modifier: Modifier,
    openProfile : () -> Unit
) {

    val animatedProgress = animateFloatAsState(
        targetValue = (.5f),
        animationSpec = tween(
            delayMillis = 1500,
            durationMillis = 1500,
            easing = LinearEasing
        ), label = ""
    ).value

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = if (isSystemInDarkTheme()) {
                    getNightDarkColor()
                } else {
                    getNightLightColor()
                }
            )
            .padding(20.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {

            /**
             * Top row to show profile image and drawer close button
             * **/
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                /**
                 * User Profile with Progress bar for total tasks completed
                 * **/
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable(
                            onClick = openProfile
                        )
                        .padding(0.dp), contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(Res.drawable.app_icon),
                        contentDescription = "avatarImage",
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                            .clip(shape = RoundedCornerShape(80.dp))
                    )

                    CircularProgressIndicator(
                        progress = { animatedProgress },
                        modifier = Modifier
                            .progressSemantics()
                            .size(100.dp),
                        color = NightDotooBrightPink,
                        trackColor = getLightThemeColor(),
                    )
                }


                /**
                 * Drawer close icon button
                 * **/
                IconButton(
                    onClick = { closeDrawer() },
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .border(
                            width = 1.dp,
                            color = getLightThemeColor(),
                            shape = RoundedCornerShape(40.dp)
                        )

                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Button to close side drawer.",
                        tint = Color.White
                    )
                }

            }


            /**
             * Breaking user name into separate lines
             * **/
            var userNameMultiline = ""
            val userName = userName
                .split(" ", limit = 3)
                .toCollection(ArrayList())
            for (word in userName) {
                userNameMultiline += word
                if (word != userName.last()) {
                    userNameMultiline += "\n"
                }
            }

            Spacer(modifier = Modifier.height(30.dp))


            /**
             * Showing user name
             * **/
            Text(
                text = userNameMultiline,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                fontSize = 36.sp,
                fontFamily = RobotoFontFamily(),
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(30.dp))


            userEmail.let { email ->
                /**
                 * Showing user email
                 * **/
                Text(
                    text = email,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    fontFamily = RobotoFontFamily(),
                    fontWeight = FontWeight.ExtraBold,
                    color = LessTransparentWhiteColor
                )
                Spacer(modifier = Modifier.height(30.dp))
            }


            /**
             * Lazy column for Menu items
             * **/
            LazyColumn(modifier = Modifier.fillMaxWidth()) {

                items(menuItems) { menuItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .clickable(
                                onClick = {
                                    onMenuItemClick(menuItem)
                                }
                            ),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            menuItem.icon,
                            contentDescription = menuItem.contentDescription,
                            tint = LessTransparentWhiteColor
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = menuItem.title,
                            fontFamily = RobotoFontFamily(),
                            color = Color.White,
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.weight(1f))

            /**
             *Logout button
             * **/
            Row(
                modifier = Modifier
                    .widthIn(max = 280.dp, min = 150.dp)
                    .background(
                        color = NightTransparentWhiteColor,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                    .clickable(
                        onClick = logout
                    )
                ,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ){

                Text(
                    text = "Logout",
                    color = Color.White,
                    fontFamily = RobotoFontFamily(),

                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    Icons.Default.Lock,
                    contentDescription = "LogoUt Button",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewNavigationDrawer() {
    NavigationDrawer(
        menuItems = menuItems,
        onMenuItemClick = {},
        closeDrawer = {},
        logout = {},
        modifier = Modifier,
        openProfile = {}
    )
}