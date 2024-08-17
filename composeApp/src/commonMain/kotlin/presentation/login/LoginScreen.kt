package presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.EnumProjectColors
import common.getColor
import presentation.createproject.components.NoBorderEditText
import presentation.login.components.PolicyLineView
import presentation.login.components.SignupLineView
import presentation.login.helpers.LoginUIState
import presentation.shared.SaveButtonView
import presentation.shared.fonts.RobotoFontFamily
import presentation.theme.NightDotooBrightPink
import presentation.theme.getTextColor

@Composable
fun LoginScreen(
    navigateToPolicy: () -> Unit = {},
    navigateToSignup: () -> Unit = {}, onPasswordChanged: (password: String) -> Unit = {},
    onEmailChanged: (email: String) -> Unit = {},
    uiState: LoginUIState = LoginUIState(),
    login: () -> Unit,
) {

    val themeColor = EnumProjectColors.Blue.getColor()

    val passwordFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f))
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column {
            Text(
                text = "Login",
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 20.dp),
                color = getTextColor(),
                textAlign = TextAlign.Start,
                fontSize = 36.sp,
                fontFamily = RobotoFontFamily(),
                fontWeight = FontWeight.Black,
                lineHeight = 50.sp
            )
            Text(
                text = uiState.heading,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                fontFamily = RobotoFontFamily(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                lineHeight = 30.sp
            )

        }


        /**
         * Login form
         * **/
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.ime),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            NoBorderEditText(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth().padding(bottom = 20.dp),
                text = uiState.email,
                updateText = { onEmailChanged(it) }, focusRequester = emailFocusRequester,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                nextFieldFocusRequester = passwordFocusRequester,
                placeHolder = "Enter your email",
                label = "Email",
                showHelperText = false,
                showClearTextButtonIcon = true,
                maxLines = 1,
                fontSize = 20
            )
            //password field
            NoBorderEditText(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                text = uiState.password,
                updateText = { onPasswordChanged(it) },
                focusRequester = passwordFocusRequester,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                placeHolder = uiState.passwordPlaceholder,
                label = "Password",
                showHelperText = false,
                showClearTextButtonIcon = true,
                onDone = {
                    if (uiState.enableLoginButton) {
                        login()
                    }
                },
                visualTransformation = PasswordVisualTransformation(),
                maxLines = 1,
                fontSize = 20
            )
            //login button
            SaveButtonView(
                containerModifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 30.dp),
                label = "Lets Go!",
                onClick = {
                    if (uiState.enableLoginButton) {
                        login()
                    }
                },
                buttonThemeColor = themeColor,
                alignment = Alignment.Center,
                buttonModifier = Modifier.fillMaxWidth(),
                showIcon = false,
                fontSize = 18
            )

            //signup text button
            SignupLineView(
                navigateToSignup = navigateToSignup,
                clickableTextColor = NightDotooBrightPink,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            //policy text buttons
            PolicyLineView(
                clickableTextColor = themeColor,
                navigateToPolicy = { navigateToPolicy() },
                navigateToTermOfUse = { navigateToPolicy() }
            )
        }

    }
}