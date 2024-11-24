package di

import AppViewModel
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import data.local.preferences.createDataStore
import data.local.room.DatabaseFactory
import org.koin.core.module.dsl.singleOf
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
import presentation.shared.projectCardWithProfiles.helpers.ProjectCardWithProfilesViewModel
import presentation.signup.helpers.SignupViewModel


actual val platformModule= module {
    single{ DatabaseFactory() }
    singleOf(::LoginViewModel)
    singleOf(::SignupViewModel)
    singleOf(::DashboardViewModel)
    singleOf(::ProjectsViewModel)
    singleOf(::OnBoardingViewModel)
    singleOf(::CreateProjectViewModel)
    singleOf(::CompleteProfileViewModel)
    singleOf(::AppViewModel)
    singleOf(::ProjectViewModel)
    singleOf(::CreateTaskViewModel)
    singleOf(::ChatViewModel)
    singleOf(::ColorPickerViewModel)
    singleOf(::ProjectCardWithProfilesViewModel)
    single<DataStore<Preferences>>{ createDataStore() }
}