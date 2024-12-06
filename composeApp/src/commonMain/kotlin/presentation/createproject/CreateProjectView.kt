package presentation.createproject

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import common.getColor
import common.maxDescriptionCharsAllowed
import common.maxTitleCharsAllowed
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.createproject.components.NoBorderEditText
import presentation.createproject.helpers.CreateProjectScreenEvent
import presentation.createproject.helpers.CreateProjectUiState
import presentation.shared.dialogs.LoadingDialog
import presentation.shared.SaveButtonView
import presentation.shared.TopHeadingWithCloseButton
import presentation.shared.ai.SuggestionButtonsColumn
import presentation.shared.colorPicker.ColorPicker
import presentation.theme.getLightThemeColor

@ExperimentalResourceApi
@Composable
fun CreateProjectView(
    uiState: CreateProjectUiState = CreateProjectUiState(),
    navigateBack: () -> Unit = {},
    onScreenEvent: (CreateProjectScreenEvent) -> Unit = {},
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
            heading = "Create Project",
            onClose = {
                navigateBack()
            },
            modifier = Modifier.fillMaxHeight(0.12f)
        )

        ColorPicker(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),
            initiallySelectedColor = 4281402925,
            onColorSelected = { color ->
                onScreenEvent(CreateProjectScreenEvent.ProjectColorChanged(color))
            },
        )

        NoBorderEditText(
            modifier = Modifier.padding(20.dp),
            text = uiState.projectName,
            updateText = {
                onScreenEvent(CreateProjectScreenEvent.ProjectNameChanged(it))
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
                onScreenEvent(CreateProjectScreenEvent.CreateProject)
            },
            placeHolder = "Enter project name",
            label = "Name",
        )

        AnimatedVisibility(visible = uiState.showSuggestion){
           /* SuggestionButtonRow(
                onActionButtonClicked = {
                    onScreenEvent(CreateProjectScreenEvent.ToggleDescriptionVisibility)
                    onScreenEvent(CreateProjectScreenEvent.ToggleSuggestion)
                },
                onSkipClick = {
                    onScreenEvent(CreateProjectScreenEvent.ToggleSuggestion)
                },
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                suggestionText = "Add Description"
            )*/
            SuggestionButtonsColumn(
                onSuggestionClicked = {
                    onScreenEvent(CreateProjectScreenEvent.ToggleDescriptionVisibility)
                    onScreenEvent(CreateProjectScreenEvent.ToggleSuggestion)
                },
                onSkipClick = {
                    onScreenEvent(CreateProjectScreenEvent.ToggleSuggestion)
                },
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                suggestions = listOf("Add Description", "Add Category", "Add A Link", "Add Image")
            )
        }

        /**
         * Text field for adding description
         * **/
        AnimatedVisibility(visible = uiState.showDescription ) {
            NoBorderEditText(
                modifier = Modifier.padding(20.dp),
                text = uiState.projectDescription,
                updateText = {
                    onScreenEvent(CreateProjectScreenEvent.ProjectDescriptionChanged(it))
                },
                focusRequester = descriptionFocusRequester,
                placeHolder = "Enter description here",
                label = "Description",
                maxCharAllowed = maxDescriptionCharsAllowed,
                onDone = {
                    onScreenEvent(CreateProjectScreenEvent.CreateProject)
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )
        }

        SaveButtonView(
            label = "Create",
            onClick = {
                onScreenEvent(CreateProjectScreenEvent.CreateProject)
            },
            buttonThemeColor = uiState.projectColor.getColor(),
            enabled = uiState.enableSaveButton
        )
    }

    if (uiState.isLoading){
        LoadingDialog()
    }
}
