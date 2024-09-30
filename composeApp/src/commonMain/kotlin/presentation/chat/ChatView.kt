@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package presentation.chat

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import common.ChatScreenBottomSheetTypes
import data.local.entities.MessageEntity
import data.local.entities.UserEntity
import domain.models.Project
import presentation.chat.components.ChatViewMainContent
import presentation.chat.components.EmoticonsControllerView
import kotlinx.coroutines.launch
import presentation.theme.LessTransparentBlueColor
import presentation.theme.NightTransparentWhiteColor
import presentation.theme.getLightThemeColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatView(
    participants: List<UserEntity>,
    messages: LazyPagingItems<MessageEntity>,
    showAttachment: (messages: MessageEntity) -> Unit,
    interactOnMessage: (message: MessageEntity, emoticon: String) -> Unit,
    onClose : () -> Unit,
    project : Project?
) {

    var selectedMessage: MessageEntity? by remember {
        mutableStateOf(null)
    }

    var currentBottomSheet: ChatScreenBottomSheetTypes? by remember {
        mutableStateOf(null)
    }
    val sheetState = rememberStandardBottomSheetState(
        skipHiddenState = false,
        initialValue = SheetValue.Hidden
    )

    val sheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()

    val closeSheet = {
        scope.launch {
            sheetScaffoldState.bottomSheetState.hide()
        }
    }
    val openSheet = {
        scope.launch {
            sheetScaffoldState.bottomSheetState.expand()
        }
    }

    val transition = rememberInfiniteTransition(label = "")

    val darkTheme = isSystemInDarkTheme()

    val offsetX by transition.animateValue(
        initialValue = (500).dp,
        targetValue = 200.dp,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 20000),
            repeatMode = RepeatMode.Reverse
        ),
        typeConverter = Dp.VectorConverter, label = ""
    )
    val offsetY by transition.animateValue(
        initialValue = (150).dp,
        targetValue = 550.dp,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 30000),
            repeatMode = RepeatMode.Reverse
        ),
        typeConverter = Dp.VectorConverter, label = ""
    )

    val offsetX1 by transition.animateValue(
        initialValue = 160.dp,
        targetValue = 310.dp,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 30000),
            repeatMode = RepeatMode.Reverse
        ),
        typeConverter = Dp.VectorConverter, label = ""
    )
    val offsetY1 by transition.animateValue(
        initialValue = 550.dp,
        targetValue = 450.dp,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 20000),
            repeatMode = RepeatMode.Reverse
        ),
        typeConverter = Dp.VectorConverter, label = ""
    )

    BottomSheetScaffold(
        modifier = Modifier,
        scaffoldState = sheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            currentBottomSheet?.let {
                when (it) {
                    ChatScreenBottomSheetTypes.MESSAGE_EMOTICONS -> {
                        selectedMessage?.let { selectedMessage ->
                            EmoticonsControllerView(
                                message = selectedMessage,
                                onItemSelected = { emoticon, msg ->
                                    interactOnMessage(msg, emoticon)
                                    closeSheet()
                                }
                            )
                        }
                    }

                    ChatScreenBottomSheetTypes.CUSTOM_EMOTICONS -> TODO()
                    ChatScreenBottomSheetTypes.PERSON_TAGGER -> TODO()
                    ChatScreenBottomSheetTypes.COLLABORATOR_SCREEN -> TODO()
                }
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = getLightThemeColor()
                )
        ) {
            /**
             * Two Animated Circles in background
             * **/
            Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
                drawCircle(
                    color = if (darkTheme) {
                        NightTransparentWhiteColor
                    } else {
                        LessTransparentBlueColor
                    },
                    radius = 130.dp.toPx(),
                    center = Offset(
                        x = offsetX1.toPx(),
                        y = offsetY1.toPx()
                    )
                )
            })
            Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
                drawCircle(
                    color = if (darkTheme) {
                        NightTransparentWhiteColor
                    } else {
                        LessTransparentBlueColor
                    },
                    radius = 180.dp.toPx(),
                    center = Offset(
                        x = offsetX.toPx(),
                        y = offsetY.toPx()
                    )
                )
            })

            ChatViewMainContent(
                modifier = Modifier.padding(padding),
                messages = messages,
                openEmoticons = { message ->
                    selectedMessage = message
                    currentBottomSheet = ChatScreenBottomSheetTypes.MESSAGE_EMOTICONS
                    openSheet()
                },
                showAttachment = showAttachment,
                openCollaboratorsScreen = {
                    currentBottomSheet = ChatScreenBottomSheetTypes.COLLABORATOR_SCREEN
                    openSheet()
                },
                openPersonTagger = {
                    currentBottomSheet = ChatScreenBottomSheetTypes.PERSON_TAGGER
                    openSheet()
                },
                participants = participants,
                project = project,
                onClose = onClose
            )
        }
    }
}



