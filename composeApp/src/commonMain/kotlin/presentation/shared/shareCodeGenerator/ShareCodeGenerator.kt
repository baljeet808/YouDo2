package presentation.shared.shareCodeGenerator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.COLOR_APP_THEME_PURPLE_VALUE
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.LessTransparentWhiteColor
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.sync_24dp

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ShareCodeGenerator(

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(
                color = Color(COLOR_APP_THEME_PURPLE_VALUE).copy(alpha = 0.3f),
                shape = RoundedCornerShape(5.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Text(
                text = "YouDoToo Code",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontFamily = AlataFontFamily(),
                fontWeight = FontWeight.ExtraBold,
                color = LessTransparentWhiteColor
            )

            IconButton(
                onClick = {

                },
                modifier = Modifier
                    .weight(0.2f)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.sync_24dp),
                    contentDescription = "Button to regenerate share code",
                    tint = Color(COLOR_APP_THEME_PURPLE_VALUE)
                )
            }
        }

    }
}