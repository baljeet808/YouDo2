package presentation.shared.colorPicker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.models.ColorPalette
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.shared.colorPicker.helper.ColorPickerScreenEvent
import presentation.shared.colorPicker.helper.ColorPickerViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    initiallySelectedColor : ColorPalette?,
    onColorSelected : (ColorPalette?) -> Unit
) {

    val viewModel = koinViewModel<ColorPickerViewModel>()

    val uiState = viewModel.uiState

    if(uiState.isLoading){
        Box(
            modifier = Modifier .fillMaxSize()
                .clip(
                    shape = RoundedCornerShape(
                        topEnd = 40.dp,
                        topStart = 40.dp,
                        bottomStart = 40.dp,
                        bottomEnd = 0.dp
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
        }
    }else{
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier
        ) {
            items(items =uiState.colorList, key = {it.id}) { color ->
                ColorSquare(
                    color = color,
                    isSelected = color == uiState.selectedColor,
                    onClick = {
                        viewModel.onEvent(ColorPickerScreenEvent.ColorSelected(color))
                        onColorSelected(color)
                    }
                )
            }
        }
    }
    LaunchedEffect(key1 = Unit){
        viewModel.getColorsFromFirebase(initiallySelectedColor)
    }
}

@Composable
fun ColorSquare(color: ColorPalette, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size( if(isSelected) 40.dp else 30.dp)
            .background(color = Color(color.colorLongValue), shape = RoundedCornerShape(8.dp))
            .border(
                width = if (isSelected) 0.dp else 4.dp,
                color = if (isSelected) Color.Black else Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        // You can add an icon or indicator here if needed for selected state
        if (isSelected) {
            Icon(
                Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color.White,
            )
        }
    }
}