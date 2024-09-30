package presentation.createproject.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.EnumProjectColors
import common.getColor
import org.jetbrains.compose.resources.painterResource
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.LightAppBarIconsColor
import presentation.theme.LightDotooFooterTextColor
import presentation.theme.NightDotooFooterTextColor
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.baseline_description_add_24
import youdo2.composeapp.generated.resources.baseline_description_remove_24
import youdo2.composeapp.generated.resources.baseline_palette_24

@Composable
fun ProjectColorPickerAndDescriptionButton(
    selectedColor: EnumProjectColors,
    onColorSelected: (EnumProjectColors) -> Unit,
    showColorOptions: Boolean,
    toggleColorOptions: () -> Unit = {},
    modifier: Modifier = Modifier,
    toggleDescriptionVisibility: () -> Unit = {},
    clearProjectDescription: () -> Unit = {},
    onKeyBoardController: () -> Unit = {},
    showDescription: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        ColorSelectionButton(selectedColor) {
            toggleColorOptions()
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

    AnimatedVisibility(visible = showColorOptions) {
        ColorOptionsList(selectedColor, onColorSelected)
    }
}

@Composable
fun ColorSelectionButton(selectedColor: EnumProjectColors, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = if (isSystemInDarkTheme()) NightDotooFooterTextColor else LightDotooFooterTextColor,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.baseline_palette_24),
            contentDescription = "Button to set project color.",
            tint = selectedColor.getColor(),
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = selectedColor.name,
            color = LightAppBarIconsColor,
            fontFamily = AlataFontFamily(),
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ColorOptionsList(selectedColor: EnumProjectColors, onColorSelected: (EnumProjectColors) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        item {
            Spacer(modifier = Modifier.width(20.dp))
        }
        items(items = EnumProjectColors.entries) { color ->
            ColorOptionItem(
                color,
                selectedColor == color,
                onColorSelected = {
                    onColorSelected(color)
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun ColorOptionItem(
    color: EnumProjectColors,
    selected : Boolean,
    onColorSelected: () -> Unit
){
    Row(
        modifier = Modifier
            .border(
                width =
                if (selected) {
                    2.dp
                } else {
                    1.dp
                },
                color = if (selected) {
                    if (isSystemInDarkTheme()) {
                        Color.White
                    } else {
                        Color.Black
                    }
                } else {
                    if (isSystemInDarkTheme()) {
                        NightDotooFooterTextColor
                    } else {
                        LightDotooFooterTextColor
                    }
                },
                shape = RoundedCornerShape(30.dp)
            )
            .padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)
            .clickable(
                onClick = {
                    onColorSelected()
                }
            ),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.baseline_palette_24),
            contentDescription = "Button to set project color.",
            tint = color.getColor(),
            modifier = Modifier
                .width(25.dp)
                .height(25.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = color.name,
            color = if (selected) {
                if (isSystemInDarkTheme()) {
                    Color.White
                } else {
                    Color.Black
                }
            } else {
                LightAppBarIconsColor
            },
            fontFamily = AlataFontFamily(),
            fontSize = 15.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}