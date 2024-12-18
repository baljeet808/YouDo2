package presentation.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.EnumProjectColors
import common.getColor
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.createproject.components.NoBorderEditText
import presentation.onboarding.components.PreviousButton
import presentation.shared.dialogs.LoadingDialog
import presentation.shared.SaveButtonView
import presentation.shared.fonts.AlataFontFamily
import presentation.shared.fonts.RobotoFontFamily
import presentation.signup.helpers.SignupUIState

@ExperimentalResourceApi
@Composable
fun SignupScreen(
    uiState: SignupUIState = SignupUIState(),
    onPasswordChanged: (password: String) -> Unit = {},
    onEmailChanged: (email: String) -> Unit = {},
    signUp: () -> Unit,
    navigateBackToLogin: () -> Unit,
) {
    val themeColor = EnumProjectColors.Green.getColor()
    val contentColor = Color.White
    val buttonsColor = EnumProjectColors.Pink.getColor()
    val passwordFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = themeColor.copy(alpha = 0.4f))
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier.fillMaxHeight(0.3f)
        )  {
            Text(
                text = "Signup",
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 20.dp),
                color = contentColor,
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
                color = contentColor,
                lineHeight = 30.sp
            )

        }


        /**
         * Signup form
         * **/
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f)
                .windowInsetsPadding(WindowInsets.ime),
            verticalArrangement = Arrangement.Top
        ) {
            NoBorderEditText(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth().padding(bottom = 20.dp),
                text = uiState.email,
                updateText = { onEmailChanged(it) }, focusRequester = emailFocusRequester,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                nextFieldFocusRequester = passwordFocusRequester,
                placeHolder = "Enter your email",
                label = "Email",
                showHelperText = false,
                showClearTextButtonIcon = true,
                maxLines = 1,
                fontSize = 20,
                labelColor = contentColor
            )
            //password field
            NoBorderEditText(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                text = uiState.password,
                updateText = { onPasswordChanged(it) },
                focusRequester = passwordFocusRequester,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                placeHolder = uiState.passwordPlaceholder,
                label = "Password",
                showHelperText = false,
                showClearTextButtonIcon = false,
                onDone = {
                    if (uiState.enableSignupButton) {
                        signUp()
                    }
                },
                isPasswordField = true,
                maxLines = 1,
                fontSize = 20,
                labelColor = contentColor
            )
            //login button
            SaveButtonView(
                containerModifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 30.dp),
                label = "Sign Me Up!",
                onClick = {
                    if (uiState.enableSignupButton) {
                        signUp()
                    }
                },
                buttonThemeColor = buttonsColor,
                alignment = Alignment.Center,
                buttonModifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                showIcon = false,
                fontSize = 18,
                labelColor = contentColor,
                enabled = uiState.enableSignupButton
            )
        }

    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            PreviousButton(
                label = "Login",
                backgroundColor = buttonsColor,
                onClick = {
                    navigateBackToLogin()
                },
                contentColor = contentColor
            )
            Text(
                text = "Already have an account?",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = contentColor,
                    fontSize = 16.sp,
                    fontFamily = AlataFontFamily()
                ),
            )
        }

    }

    if(uiState.isLoading){
        LoadingDialog()
    }
}