package presentation.complete_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.EnumProjectColors
import common.getColor
import presentation.complete_profile.helpers.CompleteProfileUIState
import presentation.shared.SaveButtonView
import presentation.theme.getNightDarkColor
import presentation.theme.getNightLightColor

@Composable
fun CompleteProfileScreen(
    uiState: CompleteProfileUIState,
    updateName: (String) -> Unit,
    updateAvatarUrl: (String) -> Unit,
    attemptSaveProfile: () -> Unit,
    skip: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize().background(color = if (isSystemInDarkTheme()) {
            getNightDarkColor()
        } else {
            getNightLightColor()
        }),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Complete Profile Screen",
            modifier = Modifier
        )
    }
    SaveButtonView(
        onClick = {
            skip()
        },
        label = "Skip",
        alignment = Alignment.BottomStart,
        buttonThemeColor = EnumProjectColors.Blue.name.getColor(),
        showIcon = false
    )
}