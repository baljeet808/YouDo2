package presentation.create_task

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.DueDates
import common.EnumCreateTaskSheetType
import common.SUGGESTION_ADD_DESCRIPTION
import common.formatNicelyWithoutYear
import common.getColor
import common.maxDescriptionCharsAllowed
import common.maxTitleCharsAllowed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.create_task.helpers.CreateTaskScreenEvent
import presentation.create_task.helpers.CreateTaskUiState
import presentation.createproject.components.NoBorderEditText
import presentation.shared.SaveButtonView
import presentation.shared.TopHeadingWithCloseButton
import presentation.shared.ai.SuggestionButtonRow
import presentation.shared.bottomSheets.DueDatesSheet
import presentation.shared.bottomSheets.PrioritySheet
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.LightAppBarIconsColor
import presentation.theme.LightDotooFooterTextColor
import presentation.theme.NightDotooFooterTextColor
import presentation.theme.getDayDarkColor
import presentation.theme.getLightThemeColor
import presentation.theme.getNightDarkColor
import presentation.theme.getTextColor
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.baseline_calendar_month_24
import youdo2.composeapp.generated.resources.baseline_topic_24
import youdo2.composeapp.generated.resources.task_alt_24dp

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalResourceApi
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
             * Top Heading with close button
             * **/
            TopHeadingWithCloseButton(
                heading = "Create Task",
                onClose = {
                    navigateBack()
                },
                modifier = Modifier.fillMaxHeight(0.12f)
            )

            /**
             * Row for Due date and Project selection
             * **/
            /*Row(
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
                        tint = uiState.project?.color?.getColor()?: Color(0xff3F292B),
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
            */

            /*Text(
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
            Spacer(modifier = Modifier.height(20.dp))*/

            /**
             * Row for dotoo additional fields
             * **/
           /* PriorityAndDescriptionButton(
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
            */


            NoBorderEditText(
                modifier = Modifier.padding(20.dp),
                text = uiState.taskName,
                updateText = {
                    onScreenEvent(CreateTaskScreenEvent.TaskNameChanged(it))
                },
                focusRequester = titleFocusRequester,
                maxCharAllowed = maxTitleCharsAllowed,
                keyboardOptions = KeyboardOptions(
                    imeAction = if (uiState.showDescription) {
                        ImeAction.Next
                    } else {
                        ImeAction.Done
                    }
                ),
                nextFieldFocusRequester = descriptionFocusRequester,
                onDone = {
                    onScreenEvent(CreateTaskScreenEvent.CreateTask)
                },
                placeHolder = "Enter task",
                label = "Name",
            )

            AnimatedVisibility(visible = uiState.showSuggestion){
                SuggestionButtonRow(
                    onActionButtonClicked = {
                        onScreenEvent(CreateTaskScreenEvent.ToggleDescriptionVisibility)
                        onScreenEvent(CreateTaskScreenEvent.ToggleSuggestion)
                    },
                    onSkipClick = {
                        onScreenEvent(CreateTaskScreenEvent.ToggleSuggestion)
                    },
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                    suggestionText = SUGGESTION_ADD_DESCRIPTION
                )
            }

            /**
             * Text field for adding description
             * **/
            AnimatedVisibility(visible = uiState.showDescription ) {
                NoBorderEditText(
                    modifier = Modifier.padding(20.dp),
                    text = uiState.taskDescription,
                    updateText = {
                        onScreenEvent(CreateTaskScreenEvent.TaskDescriptionChanged(it))
                    },
                    focusRequester = descriptionFocusRequester,
                    placeHolder = "Enter description here",
                    label = "Description",
                    maxCharAllowed = maxDescriptionCharsAllowed,
                    onDone = {
                        onScreenEvent(CreateTaskScreenEvent.CreateTask)
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
            }
            /**
             * Save button
             * **/

            SaveButtonView(
                label = "Create",
                onClick = {
                    onScreenEvent(CreateTaskScreenEvent.CreateTask)
                },
                buttonThemeColor = uiState.projectColor.getColor(),
                enabled = uiState.enableSaveButton,
                fontSize = 20.sp,
                iconDrawableResource = Res.drawable.task_alt_24dp,
                buttonModifier = Modifier
                    .border(
                        width = 2.dp,
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White
                    )
            )
            LaunchedEffect(key1 = Unit) {
                keyBoardController?.show()
                delay(500)
                titleFocusRequester.requestFocus()
                getData()
            }
        }
    }
}
