package presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.shared.fonts.ReenieBeanieFontFamily
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.app_icon
import youdo2.composeapp.generated.resources.app_name


@Composable
fun LoginScreen(component: LoginComponent) {

    val email by component.email.subscribeAsState()
    val password by component.password.subscribeAsState()

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
        OutlinedTextField(
            value = email,
            onValueChange = {
                component.onEvent(LoginEvents.UpdateEmail(it))
            },
            label = {
                Text(text = "Email")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(16.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                component.onEvent(LoginEvents.UpdatePassword(it))
            },
            label = {
                Text(text = "Password")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(16.dp)
        )
        Button(
            onClick = {
                component.onEvent(LoginEvents.NavigateToDashboard)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            Text("Login")
        }
    }
}