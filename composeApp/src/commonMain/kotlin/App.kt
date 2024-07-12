import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.shared.fonts.ReenieBeanieFontFamily
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.app_icon
import youdo2.composeapp.generated.resources.app_name


@Composable
@Preview
fun App() {
    MaterialTheme {
       Column (
         modifier = Modifier
             .fillMaxSize(),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.SpaceEvenly
       ){
           Image(
               painter = painterResource(Res.drawable.app_icon),
               contentDescription = "App Logo"
           )
           Text(
               text = stringResource(Res.string.app_name),
               fontFamily = ReenieBeanieFontFamily(),
               fontWeight = FontWeight.Bold,
               fontSize = 28.sp
           )
       }
    }
}