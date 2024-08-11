package presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.login.components.PolicyLineView
import presentation.shared.fonts.CantarellFontFamily
import presentation.shared.fonts.RobotoFontFamily
import presentation.signup.helpers.SignupUIState
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.app_icon
import youdo2.composeapp.generated.resources.signup_label

@Composable
fun SignupScreen(
    uiState: SignupUIState = SignupUIState(),
    onCredentialsUpdated: (email : String, password : String) -> Unit = {_,_ ->},
    signUp : (email: String, password: String) -> Unit = {_,_->}
) {

    var emailText by remember {mutableStateOf("")}
    var passwordText by remember {mutableStateOf("")}

    Box(
      modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Bottom
            ){
                Text(
                    text = stringResource(Res.string.signup_label),
                    modifier = Modifier,
                    fontFamily = RobotoFontFamily(),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = 38.sp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painterResource(Res.drawable.app_icon),
                    contentDescription = "app logo",
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .background(color = Color.Transparent)
                )
            }

            /**
             * Login and policy button
             * **/
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp),
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = emailText,
                    onValueChange = {
                        emailText = it
                        onCredentialsUpdated(it, passwordText)
                    },
                    label = {
                        androidx.compose.material.Text(text = "Email")
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 6.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = ""
                        )
                    },
                    isError = uiState.emailInValid,
                    supportingText = {
                        if(uiState.emailInValid){
                            androidx.compose.material.Text("Invalid email.")
                        }
                    }
                )
                OutlinedTextField(
                    value = passwordText,
                    onValueChange = {
                        passwordText = it
                        onCredentialsUpdated(emailText, it)
                    },
                    label = {
                        androidx.compose.material.Text(text = "Password")
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 10.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if(uiState.enableSignupButton) {
                                signUp(emailText, passwordText)
                            }
                        }
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "password icon"
                        )
                    },
                    isError = uiState.passwordInValid,
                    supportingText = {
                        if(uiState.passwordInValid){
                            androidx.compose.material.Text("Invalid password format. Please include at least one uppercase letter, number and special symbol.")
                        }
                    }
                )

                Button(
                    onClick = {
                        signUp(emailText, passwordText)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(shape = RoundedCornerShape(30.dp))
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(30.dp),
                            color = Color.Gray
                        ),
                    enabled = uiState.enableSignupButton
                ){
                    androidx.compose.material.Text(
                        text = "Signup",
                        fontFamily = CantarellFontFamily(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                PolicyLineView(
                    navigateToPolicy = {
                        //TODO navigate to policy
                    },
                    navigateToTermOfUse = {
                        //TODO navigate to term of use
                    }
                )

            }
        }
    }
}