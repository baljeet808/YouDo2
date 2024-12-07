package presentation.shared.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.DialogProperties
import common.COLOR_GRAPHITE_VALUE

@Composable
fun AlertDialogView(
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {},
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    confirmButtonText : String = "Confirm",
    dismissButtonText : String = "Dismiss",
    showConfirmButton : Boolean = true,
    showDismissButton : Boolean = true,
    dismissOnClickOutside : Boolean = true
) {
    AlertDialog(
        containerColor = Color(COLOR_GRAPHITE_VALUE),
        iconContentColor = Color.White,
        titleContentColor = Color.White,
        textContentColor = Color.White,
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(
                text = dialogText
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            if(showConfirmButton) {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text(confirmButtonText)
                }
            }
        },
        dismissButton = {
            if(showDismissButton) {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(dismissButtonText)
                }
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = dismissOnClickOutside
        )
    )
}