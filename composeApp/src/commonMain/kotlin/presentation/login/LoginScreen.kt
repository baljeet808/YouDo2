package presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.EnumProjectColors
import common.getColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.createproject.components.NoBorderEditText
import presentation.login.components.PolicyLineView
import presentation.login.components.SignupLineView
import presentation.login.helpers.LoginUIState
import presentation.shared.SaveButtonView
import presentation.shared.fonts.ReenieBeanieFontFamily
import presentation.shared.fonts.RobotoFontFamily
import presentation.theme.getNightDarkColor
import presentation.theme.getNightLightColor
import presentation.theme.getTextColor
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.app_icon
import youdo2.composeapp.generated.resources.app_name


@Composable
fun LoginScreen(
    navigateToPolicy: () -> Unit = {},
    navigateToSignup: () -> Unit = {},
    onCredentialsUpdated: (email: String, password: String) -> Unit = { _, _ -> },
    uiState: LoginUIState = LoginUIState(),
    login: (email: String, password: String) -> Unit = { _, _ -> },
) {

    val themeColor =  EnumProjectColors.Blue.getColor()

    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    val passwordFocusRequester = remember {
        FocusRequester()
    }
    val emailFocusRequester = remember {
        FocusRequester()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = if (isSystemInDarkTheme()) {
                    getNightDarkColor()
                } else {
                    getNightLightColor()
                }
            ).padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        /**
         * Fixed App name
         * **/
        Text(
            text = stringResource(Res.string.app_name),
            fontFamily = ReenieBeanieFontFamily(),
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
                .padding(start = 10.dp),
            maxLines = 1,
            textAlign = TextAlign.Center,
            color = themeColor
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Login",
                modifier = Modifier,
                fontFamily = RobotoFontFamily(),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 38.sp,
                color = getTextColor()
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .width(42.dp)
                    .height(42.dp)
                    .clip(
                        shape = RoundedCornerShape(50.dp)
                    ).border(
                        width = 1.dp,
                        shape = RoundedCornerShape(50.dp),
                        color = themeColor
                    )
                    .padding(1.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(Res.drawable.app_icon),
                    contentDescription = "app logo",
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }


        /**
         * Login form
         * **/
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f),
            verticalArrangement = Arrangement.Center
        ) {

            //email field

            NoBorderEditText(
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                text = emailText,
                updateText = {
                    emailText = it
                    onCredentialsUpdated(it, passwordText)
                },
                focusRequester = emailFocusRequester,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                nextFieldFocusRequester =passwordFocusRequester,
                placeHolder = "Email \uD83D\uDC26",
                label = "Email",
                showHelperText = false,
                showClearTextButtonIcon = true,
                maxLines = 1,
                fontSize = 20
            )
            //password field
            Spacer(modifier = Modifier.height(20.dp))
            NoBorderEditText(
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                text = passwordText,
                updateText = {
                    passwordText = it
                    onCredentialsUpdated(emailText, it)
                },
                focusRequester = passwordFocusRequester,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                placeHolder = "Make Me Strong\uD83D\uDCAA",
                label = "Password",
                showHelperText = false,
                showClearTextButtonIcon = true,
                onDone = {
                    if (uiState.enableLoginButton) {
                        login(emailText, passwordText)
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
                    .padding(top = 30.dp, bottom = 20.dp ),
                label = "Lets Go!",
                onClick = {
                    if (uiState.enableLoginButton) {
                        login(emailText, passwordText)
                    }
                },
                buttonThemeColor = themeColor,
                alignment = Alignment.Center,
                buttonModifier = Modifier.fillMaxWidth(),
                showIcon = false,
                fontSize = 18
            )
            Spacer(modifier = Modifier.height(20.dp))

            //signup text button
            SignupLineView(
                navigateToSignup = navigateToSignup,
                clickableTextColor = themeColor
            )

            Spacer(modifier = Modifier.height(20.dp))

            //policy text buttons
            PolicyLineView(
                clickableTextColor = themeColor,
                navigateToPolicy = {
                    navigateToPolicy()
                },
                navigateToTermOfUse = {
                    navigateToPolicy()
                }
            )
        }
    }
}