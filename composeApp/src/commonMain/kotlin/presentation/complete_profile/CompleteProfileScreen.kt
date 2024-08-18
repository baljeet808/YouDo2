package presentation.complete_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.EnumProjectColors
import common.avatars
import common.getColor
import presentation.complete_profile.helpers.CompleteProfileUIState
import presentation.createproject.components.NoBorderEditText
import presentation.drawer.components.ProfilePictureView
import presentation.shared.LoadingDialog
import presentation.shared.SaveButtonView
import presentation.shared.fonts.AlataFontFamily
import presentation.shared.fonts.RobotoFontFamily
import presentation.theme.NightTransparentWhiteColor
import presentation.theme.getTextColor

@Composable
fun CompleteProfileScreen(
    title : String = "Complete Your \nProfile",
    uiState: CompleteProfileUIState,
    updateName: (String) -> Unit,
    updateAvatarUrl: (String) -> Unit,
    attemptSaveProfile: () -> Unit,
    skip: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = EnumProjectColors.Blue.getColor().copy(alpha = 0.5f)),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier.fillMaxHeight(0.3f).padding(top = 20.dp, start = 20.dp, end = 20.dp)
        ) {
            Text(
                text = title,
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
                text = "Select an awesome avatar for yourself",
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f)
                .windowInsetsPadding(WindowInsets.ime),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Column {
                Text(
                    text = "Avatar",
                    color = getTextColor(),
                    fontSize = 13.sp,
                    fontFamily = AlataFontFamily(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 10.dp)
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item {
                        Spacer(modifier = Modifier.width(40.dp))
                    }
                    items(avatars){ avatarUrl ->
                        ProfilePictureView(
                            avatarUrl = avatarUrl,
                            onClick = {
                                updateAvatarUrl(avatarUrl)
                            },
                            progress = if (uiState.selectedAvatar == avatarUrl) 1f else 0f,
                        )
                    }
                }
            }

            NoBorderEditText(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth().padding( 20.dp),
                text = uiState.userName,
                updateText = { updateName(it) },
                placeHolder = "What do we call you?",
                label = "Name",
                showHelperText = false,
                showClearTextButtonIcon = true,
                maxLines = 1,
                fontSize = 20,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                onDone = {
                    if (uiState.enableSaveButton) {
                        attemptSaveProfile()
                    }
                }
            )
            Column {

                Text(
                    text = "You can always change this later",
                    color = getTextColor(),
                    fontSize = 13.sp,
                    fontFamily = AlataFontFamily(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 10.dp),
                )
                SaveButtonView(
                    containerModifier = Modifier
                        .fillMaxWidth()
                        .padding( bottom = 20.dp, start = 20.dp, end = 20.dp),
                    label = "Save!",
                    onClick = {
                        if (uiState.enableSaveButton) {
                            attemptSaveProfile()
                        }
                    },
                    buttonThemeColor = Color.Black,
                    alignment = Alignment.Center,
                    buttonModifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    showIcon = false,
                    fontSize = 18,
                    enabled = uiState.enableSaveButton
                )
            }
        }

    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        androidx.compose.material.Text(
            text = "Skip",
            color = Color.White,
            modifier = Modifier
                .background(
                    color = NightTransparentWhiteColor,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(10.dp)
                .clickable {
                    skip()
                },
            fontFamily = AlataFontFamily(),
            fontWeight = FontWeight.Thin
        )
    }

    if(uiState.isLoading){
        LoadingDialog()
    }

}