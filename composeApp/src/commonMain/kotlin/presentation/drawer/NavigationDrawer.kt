package presentation.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.models.MenuItem
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.drawer.components.CircularPictureViewWithProgress
import presentation.drawer.components.IconButtonView
import presentation.drawer.components.MenuItemRow
import presentation.shared.fonts.RobotoFontFamily
import presentation.theme.LessTransparentWhiteColor
import presentation.theme.NightTransparentWhiteColor
import presentation.theme.getNightDarkColor
import presentation.theme.getNightLightColor

@ExperimentalResourceApi
@Composable
fun NavigationDrawer(
    userEmail: String = "",
    userName: String = "",
    avatarUrl: String = "",
    menuItems: List<MenuItem>,
    onMenuItemClick: (MenuItem) -> Unit,
    closeDrawer: () -> Unit,
    logout: () -> Unit,
    modifier: Modifier,
    openProfile: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
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
                CircularPictureViewWithProgress(
                    onClick = openProfile,
                    progress = 1f,
                    avatarUrl = avatarUrl
                )
                IconButtonView(
                    onClick = {
                        closeDrawer()
                    }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            /**
             * Breaking user name into separate lines
             * **/
            val userNameMultiline = userName.split(" ", limit = 3).joinToString("\n")
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

            /**
             * Showing user email
             * **/
            Text(
                text = userEmail,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontFamily = RobotoFontFamily(),
                fontWeight = FontWeight.ExtraBold,
                color = LessTransparentWhiteColor
            )
            Spacer(modifier = Modifier.height(30.dp))



            /**
             * Lazy column for Menu items
             * **/
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(menuItems) { menuItem ->
                    MenuItemRow(
                        menuItem = menuItem,
                        onMenuItemClick = onMenuItemClick
                    )
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
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {

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
