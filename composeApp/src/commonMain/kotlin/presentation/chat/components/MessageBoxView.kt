package presentation.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import common.getColor
import common.getRandomColor
import domain.models.Project
import org.jetbrains.compose.resources.painterResource
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.getDarkThemeColor
import presentation.theme.getTextColor
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.baseline_alternate_email_24
import youdo2.composeapp.generated.resources.baseline_attach_file_24
import youdo2.composeapp.generated.resources.baseline_camera_alt_24
import youdo2.composeapp.generated.resources.baseline_person_add_24

@Composable
fun MessageBoxView(
    openCollaboratorsScreen: () -> Unit,
    openPersonTagger: () -> Unit,
    openCamera: () -> Unit,
    project: Project?
) {

    val viewModel : MessageBoxViewModel = viewModel()

    val scope = rememberCoroutineScope()

 

    var message by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = getDarkThemeColor()
            )
    ) {
        

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextField(
                value = message,
                onValueChange = {
                    message = it
                },
                textStyle = TextStyle(
                    fontFamily = AlataFontFamily(),
                    fontSize = 16.sp,
                    color = getTextColor()
                ),
                placeholder = {
                    Text(
                        text = "Write message here...",
                        fontFamily = AlataFontFamily(),
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (message.isNotBlank()) {

                            project?.let {
                                viewModel.sendMessage(
                                    messageString = message,
                                    isUpdate = false,
                                    updateMessage = "",
                                    project = project
                                )
                            }
                            message = ""
                        }
                    }
                ),
                maxLines = 5,
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )

            Box(
                modifier = Modifier
                    .clickable(
                        onClick = {
                            if (message.isNotBlank()) {
                                project?.let {
                                    viewModel.sendMessage(
                                        messageString = message,
                                        isUpdate = false,
                                        updateMessage = "",
                                        project = project
                                    )
                                }
                                message = ""
                            }
                        }
                    )
                    .background(
                        color = if (message.isNotBlank()) {
                            project?.color?.getColor() ?: getRandomColor().getColor()
                        } else {
                            Color.Gray
                        },
                        shape = RoundedCornerShape(25.dp)
                    )
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send message button",
                    tint = Color.White,
                    modifier = Modifier
                        .width(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(20.dp))
        }


        /**
         * Bottom row of all buttons
         * **/
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    //show dialog that attachments feature is in development right now
                }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.baseline_attach_file_24),
                    contentDescription = "Attachments Button",
                    tint = Color.Gray
                )
            }

            IconButton(
                onClick = openPersonTagger
            ) {
                Icon(
                    painter = painterResource(Res.drawable.baseline_alternate_email_24),
                    contentDescription = "Mention button",
                    tint = Color.Gray
                )
            }

            IconButton(
                onClick = openCollaboratorsScreen
            ) {
                Icon(
                    painter = painterResource(Res.drawable.baseline_person_add_24),
                    contentDescription = "Add person button",
                    tint = Color.Gray
                )
            }

            IconButton(
                onClick = openCamera
            ) {
                Icon(
                    painter = painterResource(Res.drawable.baseline_camera_alt_24),
                    contentDescription = "Open Camera button",
                    tint = Color.Gray
                )
            }
        }
    }
}
