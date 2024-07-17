package presentation.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import common.getOnBoardPagerContentList
import org.jetbrains.compose.resources.stringResource
import presentation.shared.fonts.CantarellFontFamily
import presentation.shared.fonts.ReenieBeanieFontFamily
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.app_name


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(component: LoginComponent) {

    val email by component.email.subscribeAsState()
    val password by component.password.subscribeAsState()

    val list = getOnBoardPagerContentList()

    val pagerState = rememberPagerState(pageCount = { list.count() })

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
                    .weight(.56F)
            ) {
                OnboardingPager(pagerContent = list[it])
            }


            AnimatedVisibility(
                pagerState.currentPage == 2,
                ) {
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
                        value = email,
                        onValueChange = {
                            component.onEvent(LoginEvents.UpdateEmail(it))
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
                        }
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
                            .padding(top = 6.dp, bottom = 10.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                component.onEvent(LoginEvents.NavigateToDashboard)
                            }
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "password icon"
                            )
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(shape = RoundedCornerShape(30.dp))
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(30.dp),
                                color = Color.Gray
                            )
                            .clickable(
                                onClick = {
                                    component.onEvent(LoginEvents.NavigateToDashboard)
                                }
                            ),
                        horizontalArrangement = Arrangement.spacedBy(
                            10.dp,
                            alignment = Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Login",
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
}