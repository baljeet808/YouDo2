package presentation.create_task.components

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.LightAppBarIconsColor
import presentation.theme.LightDotooFooterTextColor
import presentation.theme.NightDotooFooterTextColor
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.baseline_description_add_24
import youdo2.composeapp.generated.resources.baseline_description_remove_24
import youdo2.composeapp.generated.resources.breaking_news_24


@ExperimentalResourceApi
@Composable
fun PriorityAndDescriptionButton(
    onPriorityClicked: () -> Unit = {},
    selectedPriority: String = "",
    onKeyBoardController: () -> Unit = {},
    showDescription: Boolean = false,
    clearProjectDescription: () -> Unit = {},
    toggleDescriptionVisibility: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    /**
     * Row for dotoo additional fields
     * **/
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextButton(
            onClick = {
                onPriorityClicked()
            },
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = if (isSystemInDarkTheme()) {
                        NightDotooFooterTextColor
                    } else {
                        LightDotooFooterTextColor
                    },
                    shape = RoundedCornerShape(30.dp)
                )
        ) {
            Icon(
                painter = painterResource(Res.drawable.breaking_news_24),
                contentDescription = "Button to set priority",
                tint = LightAppBarIconsColor,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = selectedPriority,
                color = LightAppBarIconsColor,
                fontFamily = AlataFontFamily(),
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }

        TextButton(
            onClick = {
                toggleDescriptionVisibility()
                if (showDescription) {
                    onKeyBoardController()
                }else{
                    clearProjectDescription()
                }
            },
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = if (isSystemInDarkTheme()) {
                        NightDotooFooterTextColor
                    } else {
                        LightDotooFooterTextColor
                    },
                    shape = RoundedCornerShape(30.dp)
                )
        ) {
            Icon(
                painter = if (showDescription) {
                    painterResource(Res.drawable.baseline_description_remove_24)
                } else {
                    painterResource(Res.drawable.baseline_description_add_24)
                },
                contentDescription = "Button to set add description to project.",
                tint = LightAppBarIconsColor
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = if (showDescription) {
                    "Clear Description"
                } else {
                    "Add Description"
                },
                color = LightAppBarIconsColor,
                fontFamily = AlataFontFamily(),
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}