package presentation.shared.bottomSheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.DueDates
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.LightDotooFooterTextColor
import presentation.theme.NightDotooBrightBlue
import presentation.theme.NightDotooFooterTextColor
import presentation.theme.getDarkThemeColor

@ExperimentalResourceApi
@Composable
fun DueDatesSheet(
    dueDate: DueDates,
    onDateSelected: (DueDates) -> Unit,
    onDatePickerSelected: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = getDarkThemeColor(),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(10.dp)
    ) {

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Text(
                text = "Select Due Date",
                fontFamily = AlataFontFamily(),
                fontSize = 24.sp,
                color = if (isSystemInDarkTheme()) {
                    Color.White
                } else {
                    Color.Black
                },
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(20.dp))
            for (date in DueDates.entries.toTypedArray().toCollection(ArrayList())) {
                Spacer(modifier = Modifier.height(10.dp))

                Row(
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
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = date.toString,
                        color = if (isSystemInDarkTheme()) {
                            Color.White
                        } else {
                            Color.Black
                        },
                        fontFamily = AlataFontFamily(),
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                    )

                    Checkbox(
                        checked = date == dueDate,
                        onCheckedChange = {
                            if(date == DueDates.CUSTOM){
                                onDatePickerSelected()
                            }
                            onDateSelected(date)
                        },
                        colors = CheckboxDefaults.colors(
                            checkmarkColor = Color.White,
                            checkedColor = NightDotooBrightBlue,
                            uncheckedColor = if (isSystemInDarkTheme()) {
                                Color.White
                            } else {
                                Color.Black
                            }
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}