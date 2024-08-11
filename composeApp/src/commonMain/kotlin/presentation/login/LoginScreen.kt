package presentation.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.OutlinedTextField
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
import common.getOnBoardPagerContentList
import org.jetbrains.compose.resources.stringResource
import presentation.login.components.OnboardingPager
import presentation.login.components.PolicyLineView
import presentation.login.components.SignupLineView
import presentation.login.helpers.LoginUIState
import presentation.shared.fonts.CantarellFontFamily
import presentation.shared.fonts.ReenieBeanieFontFamily
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.app_name


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(
    navigateToPolicy: () -> Unit = {},
    navigateToSignup: () -> Unit = {},
    onCredentialsUpdated: (email : String, password : String) -> Unit = {_,_ ->},
    uiState: LoginUIState = LoginUIState(),
    login: (email : String, password : String) -> Unit = {_,_ ->},
) {

    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    //pager related setup
    val list = getOnBoardPagerContentList()
    val pagerState = rememberPagerState(pageCount = { list.count() })

    //login ui
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
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
                    .heightIn(max = 100.dp)
                    .padding(start = 10.dp),
                maxLines = 1,
                textAlign = TextAlign.Center
            )


            /**
             *On boarding screens
             * **/
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.50F)
            ) {
                OnboardingPager(pagerContent = list[it])
            }


            /**
             * Login form
             * **/
            AnimatedVisibility( visible = pagerState.currentPage == 2) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp),
                    verticalArrangement = Arrangement.Center
                ) {

                    //email field
                    OutlinedTextField(
                        value = emailText,
                        onValueChange = {
                            emailText = it
                            onCredentialsUpdated(it,passwordText)
                        },
                        label = {
                            Text(text = "Email")
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
                                Text("Invalid email.")
                            }
                        }
                    )

                    //password field
                    OutlinedTextField(
                        value = passwordText,
                        onValueChange = {
                            passwordText = it
                            onCredentialsUpdated(emailText,it)
                        },
                        label = {
                            Text(text = "Password")
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
                                if(uiState.enableLoginButton) {
                                    login(emailText, passwordText)
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
                                Text("Invalid password format. Please include at least one uppercase letter, number and special symbol.")
                            }
                        }
                    )

                    //login button
                    Button(
                        onClick = {
                            login(emailText, passwordText)
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
                        enabled = uiState.enableLoginButton
                    ){
                        if(uiState.isLoading){
                            CircularProgressIndicator(
                                modifier = Modifier,
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        }else{
                            Text(
                                text = "Login",
                                fontFamily = CantarellFontFamily(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    //signup text button
                    SignupLineView( navigateToSignup = navigateToSignup )

                    Spacer(modifier = Modifier.height(20.dp))

                    //policy text buttons
                    PolicyLineView(
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
    }
}