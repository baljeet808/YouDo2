package presentation.create_task

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.DueDates
import common.EnumCreateTaskSheetType
import common.formatNicelyWithoutYear
import common.getColor
import common.getRandomColor
import common.maxDescriptionCharsAllowed
import common.maxTitleCharsAllowed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import presentation.create_task.components.PriorityAndDescriptionButton
import presentation.create_task.helpers.CreateTaskScreenEvent
import presentation.create_task.helpers.CreateTaskUiState
import presentation.shared.bottomSheets.DueDatesSheet
import presentation.shared.bottomSheets.PrioritySheet
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.DoTooRed
import presentation.theme.LightAppBarIconsColor
import presentation.theme.LightDotooFooterTextColor
import presentation.theme.NightDotooFooterTextColor
import presentation.theme.NightDotooTextColor
import presentation.theme.getDayDarkColor
import presentation.theme.getLightThemeColor
import presentation.theme.getNightDarkColor
import presentation.theme.getTextColor
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.baseline_calendar_month_24
import youdo2.composeapp.generated.resources.baseline_topic_24

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertTaskView(
    uiState: CreateTaskUiState = CreateTaskUiState(),
    navigateBack: () -> Unit,
    onScreenEvent: (CreateTaskScreenEvent) -> Unit = {},
    getData: () -> Unit
) {

    val keyBoardController = LocalSoftwareKeyboardController.current


    val titleFocusRequester = remember {
        FocusRequester()
    }
    val descriptionFocusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(key1 = uiState.showKeyboard) {
        if (uiState.showKeyboard) {
            keyBoardController?.show()
            when {
                uiState.focusOnTitle -> titleFocusRequester.requestFocus()
                uiState.focusOnDescription -> descriptionFocusRequester.requestFocus()
            }
        }
    }


    val transition = rememberInfiniteTransition(label = "")

    val rotation = transition.animateValue(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 100),
            repeatMode = RepeatMode.Reverse
        ),
        typeConverter = Float.VectorConverter, label = ""
    )

    var showTitleErrorAnimation by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = showTitleErrorAnimation) {
        delay(1000)
        showTitleErrorAnimation = false
    }

    var currentBottomSheet: EnumCreateTaskSheetType? by remember {
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


    BottomSheetScaffold(
        sheetContent = {
            currentBottomSheet?.let {
                when (it) {
                    EnumCreateTaskSheetType.SELECT_PRIORITY -> {
                        PrioritySheet(priority = uiState.priority) { newPriority ->
                            onScreenEvent(CreateTaskScreenEvent.TaskPriorityChanged(newPriority))
                            closeSheet()
                        }
                    }

                    EnumCreateTaskSheetType.SELECT_DUE_DATE -> {
                        DueDatesSheet(
                            dueDate = uiState.dueDate,
                            onDateSelected = { selectedDate ->
                                onScreenEvent(CreateTaskScreenEvent.TaskDueDateChanged(selectedDate))
                                closeSheet()
                            },
                            onDatePickerSelected = {
                                onScreenEvent(CreateTaskScreenEvent.TaskDueDateChanged(DueDates.CUSTOM))
                                closeSheet()
                            }
                        )
                    }
                }
            }
        },
        modifier = Modifier
            .background(
                color = if (isSystemInDarkTheme()) {
                    getNightDarkColor()
                } else {
                    getDayDarkColor()
                },
                shape = RoundedCornerShape(20.dp)
            ),
        scaffoldState = sheetScaffoldState,
        sheetPeekHeight = 0.dp
    ) {
        /**
         * Main content
         * **/
        Column(
            modifier = Modifier
                .background(
                    color = getLightThemeColor()
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            /**
             * Row for top close button
             * **/
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {

                Text(
                    text = "Create Task",
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f),
                    fontFamily = AlataFontFamily(),
                    fontSize = 28.sp,
                    color = getTextColor()
                )


                Spacer(modifier = Modifier.weight(.5f))

                IconButton(
                    onClick = navigateBack,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .border(
                            width = 2.dp,
                            color = if (isSystemInDarkTheme()) {
                                NightDotooFooterTextColor
                            } else {
                                LightDotooFooterTextColor
                            },
                            shape = RoundedCornerShape(40.dp)
                        )
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Button to close side drawer.",
                        tint = if (isSystemInDarkTheme()) {
                            NightDotooTextColor
                        } else {
                            Color.Black
                        }
                    )
                }
            }

            /**
             * Row for Due date and Project selection
             * **/
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //Due Date
                Row(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = if (isSystemInDarkTheme()) {
                                NightDotooFooterTextColor
                            } else {
                                LightDotooFooterTextColor
                            },
                            shape = RoundedCornerShape(30.dp)
                        )
                        .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
                        .clickable(
                            onClick = {
                                closeSheet()
                                keyBoardController?.hide()
                                currentBottomSheet = EnumCreateTaskSheetType.SELECT_DUE_DATE
                                openSheet()
                            }
                        ),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.baseline_calendar_month_24),
                        contentDescription = "Button to set due date for this task.",
                        tint = LightAppBarIconsColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = uiState.dueDate.name,
                        color = LightAppBarIconsColor,
                        fontFamily = AlataFontFamily(),
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                //Select Project
                Row(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = if (isSystemInDarkTheme()) {
                                NightDotooFooterTextColor
                            } else {
                                LightDotooFooterTextColor
                            },
                            shape = RoundedCornerShape(30.dp)
                        )
                        .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
                        ,
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.baseline_topic_24),
                        contentDescription = "Button to set due date for this task.",
                        tint = uiState.project?.color?.getColor()?: getRandomColor().getColor(),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = uiState.project?.name ?: "Select Project",
                        color = LightAppBarIconsColor,
                        fontFamily = AlataFontFamily(),
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }


            }

            Spacer(modifier = Modifier.height(20.dp))


            Text(
                text = "Due Date set to ".plus(
                    uiState.dueDate.getExactDate().formatNicelyWithoutYear()
                ),
                color = getTextColor(),
                fontFamily = AlataFontFamily(),
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 30.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            /**
             * Row for dotoo additional fields
             * **/
            PriorityAndDescriptionButton(
                modifier = Modifier.fillMaxHeight(0.2f),
                onPriorityClicked = {
                    closeSheet()
                    keyBoardController?.hide()
                    currentBottomSheet = EnumCreateTaskSheetType.SELECT_PRIORITY
                    openSheet()
                },
                onKeyBoardController = {
                    keyBoardController?.show()
                    descriptionFocusRequester.requestFocus()
                },
                showDescription = uiState.showDescription,
                clearProjectDescription = {
                    onScreenEvent(CreateTaskScreenEvent.TaskDescriptionChanged(""))
                },
                toggleDescriptionVisibility = {
                    onScreenEvent(CreateTaskScreenEvent.ToggleDescriptionVisibility)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))
            /**
             * Text field for adding title
             * **/
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalArrangement = Arrangement.spacedBy(
                    1.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Title",
                    color = LightAppBarIconsColor,
                    fontSize = 13.sp,
                    fontFamily = AlataFontFamily(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp)
                )
                TextField(
                    value = uiState.taskName,
                    onValueChange = {
                        if (it.length <= maxTitleCharsAllowed) {
                            onScreenEvent(CreateTaskScreenEvent.TaskNameChanged(it))
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        disabledContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = "Enter new task",
                            color = getTextColor(),
                            fontSize = 24.sp,
                            fontFamily = AlataFontFamily(),
                            modifier = Modifier
                                .rotate(
                                    if (showTitleErrorAnimation) {
                                        rotation.value
                                    } else {
                                        0f
                                    }
                                )
                        )
                    },
                    textStyle = TextStyle(
                        color = getTextColor(),
                        fontSize = 24.sp,
                        fontFamily = AlataFontFamily()
                    ),
                    maxLines = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(titleFocusRequester)
                        .onFocusEvent {
                            if (it.hasFocus) {
                                closeSheet()
                            }
                        },
                    keyboardOptions = KeyboardOptions(
                        imeAction = if (uiState.showDescription) {
                            ImeAction.Next
                        } else {
                            ImeAction.Done
                        }
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            descriptionFocusRequester.requestFocus()
                        },
                        onDone = {
                            if (uiState.taskName.isNotBlank()) {
                                onScreenEvent(CreateTaskScreenEvent.CreateTask)
                            } else {
                                onScreenEvent(CreateTaskScreenEvent.TaskNameChanged(""))
                                showTitleErrorAnimation = true
                            }
                        }
                    )
                )
                Text(
                    text = "${uiState.taskName.length}/$maxTitleCharsAllowed",
                    color = if (uiState.taskName.length >= maxTitleCharsAllowed) {
                        DoTooRed
                    } else {
                        LightAppBarIconsColor
                    },
                    fontSize = 13.sp,
                    fontFamily = AlataFontFamily(),
                    modifier = Modifier.padding(start = 15.dp)
                )
            }

            AnimatedVisibility(visible = uiState.showDescription) {
                Spacer(modifier = Modifier.height(40.dp))
            }

            /**
             * Text field for adding description
             * **/
            AnimatedVisibility(visible = uiState.showDescription) {


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        1.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Description",
                        color = LightAppBarIconsColor,
                        fontSize = 13.sp,
                        fontFamily = AlataFontFamily(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp)
                    )
                    TextField(
                        value = uiState.taskDescription,
                        onValueChange = {
                            if (it.length <= maxDescriptionCharsAllowed) {
                                onScreenEvent(CreateTaskScreenEvent.TaskDescriptionChanged(it))
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            disabledContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = {
                            Text(
                                text = "Enter description here",
                                color = getTextColor(),
                                fontSize = 16.sp,
                                fontFamily = AlataFontFamily()
                            )
                        },
                        textStyle = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                            fontFamily = AlataFontFamily()
                        ),
                        maxLines = 3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(descriptionFocusRequester)
                            .onFocusEvent {
                                if (it.hasFocus) {
                                    closeSheet()
                                }
                            },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (uiState.taskName.isNotBlank()) {
                                    onScreenEvent(CreateTaskScreenEvent.CreateTask)
                                } else {
                                    onScreenEvent(CreateTaskScreenEvent.TaskDescriptionChanged(""))
                                    showTitleErrorAnimation = true
                                }
                            }
                        )
                    )
                    Text(
                        text = "${uiState.taskDescription.length}/$maxDescriptionCharsAllowed",
                        color = if (uiState.taskDescription.length >= maxDescriptionCharsAllowed) {
                            DoTooRed
                        } else {
                            LightAppBarIconsColor
                        },
                        fontSize = 13.sp,
                        fontFamily = AlataFontFamily(),
                        modifier = Modifier.padding(start = 15.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.weight(1f))

            /**
             * Save button
             * **/
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.End
            ) {

                Row(
                    modifier = Modifier
                        .shadow(elevation = 5.dp, shape = RoundedCornerShape(30.dp))
                        .background(
                            color = uiState.project?.color?.getColor()
                                ?: getRandomColor().getColor(),
                            shape = RoundedCornerShape(30.dp)
                        )
                        .padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                        .clickable(
                            onClick = {
                                if (uiState.taskName.isNotBlank()) {
                                    onScreenEvent(CreateTaskScreenEvent.CreateTask)
                                } else {
                                    onScreenEvent(CreateTaskScreenEvent.TaskNameChanged(""))
                                    showTitleErrorAnimation = true
                                }
                            }
                        )
                ) {
                    Text(
                        text = "New Task",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = AlataFontFamily(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        Icons.Default.Done,
                        contentDescription = "Create task button",
                        tint = Color.White
                    )
                }
            }

            LaunchedEffect(key1 = Unit) {
                keyBoardController?.show()
                delay(500)
                titleFocusRequester.requestFocus()
                getData()
            }
        }
    }
}
