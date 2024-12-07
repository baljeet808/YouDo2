package presentation.shared.colorPicker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.models.ColorPalette
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.viewmodel.koinViewModel
import presentation.shared.colorPicker.helper.ColorPickerScreenEvent
import presentation.shared.colorPicker.helper.ColorPickerViewModel
import presentation.shared.fonts.AlataFontFamily

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    initiallySelectedColor : Long?,
    onColorSelected : (ColorPalette) -> Unit
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
        Column(modifier = Modifier.height(120.dp)) {
            Text(
                text = "Give it a color",
                modifier = Modifier.align(Alignment.Start).padding(start = 20.dp),
                fontFamily = AlataFontFamily(),
                fontSize = 12.sp,
                color = Color.White
            )
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
    }
    LaunchedEffect(key1 = Unit){
        viewModel.getColorsFromFirebase(initiallySelectedColor)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ColorSquare(color: ColorPalette, isSelected: Boolean, onClick: () -> Unit) {
    // Smooth animation for size change
    val animatedSize by animateDpAsState(targetValue = if (isSelected) 46.dp else 36.dp)


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(48.dp)
    ) {

        Box(
            modifier = Modifier
                .size( animatedSize )
                .background(
                    color = Color(color.colorLongValue),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            // You can add an icon or indicator here if needed for selected state
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = Color.White,
                )
            }
        }
        AnimatedVisibility(visible = isSelected) {
            Text(
                text = color.paletteName,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top = 4.dp),
                fontFamily = AlataFontFamily(),
                fontSize = 10.sp,
                color = Color.White
            )
        }
    }
}