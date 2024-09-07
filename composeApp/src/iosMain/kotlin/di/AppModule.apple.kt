package di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import data.local.room.YouDo2Database
import data.local.room.getYouDo2Database
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import presentation.signup.helpers.SignupViewModel
import presentation.dashboard.helpers.DashboardViewModel
import presentation.login.helpers.LoginViewModel
import presentation.projects.helpers.ProjectsViewModel
import presentation.onboarding.helpers.OnBoardingViewModel
import presentation.createproject.helpers.CreateProjectViewModel
import presentation.complete_profile.helpers.CompleteProfileViewModel
import presentation.create_task.helpers.CreateTaskViewModel
import presentation.project.helpers.ProjectViewModel
import AppViewModel
import data.local.preferences.createDataStore


actual val platformModule= module {
    single<YouDo2Database> { getYouDo2Database() }
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
    single<DataStore<Preferences>>{ createDataStore() }
}