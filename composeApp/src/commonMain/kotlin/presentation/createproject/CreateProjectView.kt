package presentation.createproject

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import common.getColor
import common.maxDescriptionCharsAllowed
import common.maxTitleCharsAllowed
import presentation.createproject.components.NoBorderEditText
import presentation.createproject.components.ProjectColorPicker
import presentation.createproject.components.ProjectPreviewAndDescriptionButton
import presentation.createproject.helpers.CreateProjectScreenEvent
import presentation.createproject.helpers.CreateProjectUiState
import presentation.shared.SaveButtonView
import presentation.shared.TopHeadingWithCloseButton
import presentation.theme.getLightThemeColor

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

        /**
         * Row for preview and project color button
         * **/
        ProjectColorPicker(
            selectedColor = uiState.projectColor,
            onColorSelected = { color ->
                onScreenEvent(CreateProjectScreenEvent.ProjectColorChanged(color))
            },
            showColorOptions = uiState.showColorOptions,
            toggleColorOptions = {
                onScreenEvent(CreateProjectScreenEvent.ToggleColorOptions)
            },
            modifier = Modifier
        )


        ProjectPreviewAndDescriptionButton(
            modifier = Modifier.fillMaxHeight(0.15f),
            onPreviewClicked = {

            },
            onkeyBoardController = {
                keyBoardController?.show()
                descriptionFocusRequester.requestFocus()
            },
            showDescription = uiState.showDescription,
            clearProjectDescription = {
                onScreenEvent(CreateProjectScreenEvent.ProjectDescriptionChanged(""))
            },
            toggleDescriptionVisibility = {
                onScreenEvent(CreateProjectScreenEvent.ToggleDescriptionVisibility)
            }
        )


        NoBorderEditText(
            modifier = Modifier.fillMaxHeight(0.25f),
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


        /**
         * Text field for adding description
         * **/
        AnimatedVisibility(visible = uiState.showDescription) {
            NoBorderEditText(
                modifier = Modifier.fillMaxHeight(0.25f),
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
            modifier = Modifier,
            label = "Create",
            onClick = {
                onScreenEvent(CreateProjectScreenEvent.CreateProject)
            },
            buttonThemeColor = uiState.projectColor.name.getColor()
        )
    }

}
