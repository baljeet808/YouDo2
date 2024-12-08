package di

import AppViewModel
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import data.local.preferences.createDataStore
import data.local.room.DatabaseFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import presentation.chat.ChatViewModel
import presentation.complete_profile.helpers.CompleteProfileViewModel
import presentation.create_task.helpers.CreateTaskViewModel
import presentation.createproject.helpers.CreateProjectViewModel
import presentation.dashboard.helpers.DashboardViewModel
import presentation.login.helpers.LoginViewModel
import presentation.onboarding.helpers.OnBoardingViewModel
import presentation.project.helpers.ProjectViewModel
import presentation.projects.helpers.ProjectsViewModel
import presentation.shared.colorPicker.helper.ColorPickerViewModel
import presentation.shared.shareCodeGenerator.helper.CodeGeneratorViewModel
import presentation.signup.helpers.SignupViewModel

actual val platformModule= module {
    single{ DatabaseFactory(androidContext()) }
    viewModelOf(::LoginViewModel)
    viewModelOf(::SignupViewModel)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::AppViewModel)
    viewModelOf(::ProjectsViewModel)
    viewModelOf(::OnBoardingViewModel)
    viewModelOf(::CreateProjectViewModel)
    viewModelOf(::CompleteProfileViewModel)
    viewModelOf(::ProjectViewModel)
    viewModelOf(::CreateTaskViewModel)
    viewModelOf(::ChatViewModel)
    viewModelOf(::ColorPickerViewModel)
    viewModelOf(::CodeGeneratorViewModel)
    single<DataStore<Preferences>> { createDataStore(androidContext()) }
}