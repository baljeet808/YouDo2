package presentation.shared.shareCodeGenerator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.COLOR_APP_THEME_PURPLE_VALUE
import common.COLOR_GRAPHITE_VALUE
import domain.models.User
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.shared.fonts.AlataFontFamily
import presentation.shared.fonts.RobotoFontFamily
import presentation.shared.shareCodeGenerator.helper.CodeGeneratorViewModel
import presentation.theme.LessTransparentWhiteColor
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.sync_24dp

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ShareCodeGenerator(
    user: User
) {

    val clipboardManager = LocalClipboardManager.current

    val viewModel = koinViewModel<CodeGeneratorViewModel>()

    viewModel.setInitialCode(user = user)

    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(COLOR_GRAPHITE_VALUE).copy(alpha = 0.3f),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "YOUR SHARING CODE",
                modifier = Modifier,
                textAlign = TextAlign.Start,
                fontSize = 11.sp,
                fontFamily = RobotoFontFamily(),
                fontWeight = FontWeight.ExtraBold,
                color = LessTransparentWhiteColor
            )
           /*
            IconButton(
                onClick = {
                    viewModel.copyText()
                    clipboardManager.setText(AnnotatedString(uiState.code))
                },
                modifier = Modifier.size(20.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.content_copy_24dp),
                    contentDescription = "Button to copy share code",
                    tint = LessTransparentWhiteColor,
                )
            }
*/
            IconButton(
                onClick = {
                    viewModel.generateNewCode(userId = user.id)
                },
                modifier = Modifier.size(20.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.sync_24dp),
                    contentDescription = "Button to regenerate share code",
                    tint = Color(COLOR_APP_THEME_PURPLE_VALUE)
                )
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable {
                    viewModel.copyText()
                    clipboardManager.setText(AnnotatedString(uiState.code))
                }
                .background(
                    color = Color(COLOR_GRAPHITE_VALUE).copy(alpha = 0.3f),
                    shape = RoundedCornerShape(10.dp)
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = uiState.code,
                modifier = Modifier
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontFamily = AlataFontFamily(),
                fontWeight = FontWeight.ExtraBold,
                color = LessTransparentWhiteColor,
                letterSpacing = TextUnit(value = 1.5f, type = TextUnitType.Sp)
            )
        }
        AnimatedVisibility(visible = uiState.isCopying){
            Text(
                text = "Copied ✅",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                fontFamily = AlataFontFamily(),
                fontWeight = FontWeight.ExtraBold,
                color = LessTransparentWhiteColor
            )
        }
        AnimatedVisibility(visible = uiState.isLoading){
            Text(
                text = "Refreshing",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                fontFamily = AlataFontFamily(),
                fontWeight = FontWeight.ExtraBold,
                color = LessTransparentWhiteColor
            )
        }
    }
}