package presentation.complete_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import presentation.complete_profile.helpers.CompleteProfileUIState
import presentation.shared.HoverButton
import presentation.theme.getNightDarkColor

@Composable
fun CompleteProfileScreen(
    uiState: CompleteProfileUIState,
    updateName: (String) -> Unit,
    updateAvatarUrl: (String) -> Unit,
    attemptSaveProfile: () -> Unit,
    skip: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize().background(color = getNightDarkColor()),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Complete Profile Screen",
            modifier = Modifier
        )
    }
    HoverButton(
        onClick = {
            skip()
        },
        buttonLabel = "Skip",
        contentAlignment = Alignment.BottomStart
    )
}