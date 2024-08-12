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
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.EnumProjectColors
import common.getColor
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.LightAppBarIconsColor
import presentation.theme.LightDotooFooterTextColor
import presentation.theme.NightDotooFooterTextColor
import presentation.theme.getTextColor

@Composable
fun ProjectColorPicker(
    selectedColor: EnumProjectColors,
    onColorSelected: (EnumProjectColors) -> Unit,
    showColorOptions: Boolean,
    toggleColorOptions: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Select Project Color",
            color = getTextColor(),
            fontSize = 20.sp,
            fontFamily = AlataFontFamily()
        )

        ColorSelectionButton(selectedColor) {
            toggleColorOptions()
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
            Icons.Outlined.Edit,
            contentDescription = "Button to set project color.",
            tint = selectedColor.name.getColor(),
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
            Icons.Outlined.Edit,
            contentDescription = "Button to set project color.",
            tint = color.name.getColor(),
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