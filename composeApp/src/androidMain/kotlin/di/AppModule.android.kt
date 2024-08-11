package di

import AppViewModel
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import data.local.preferences.createDataStore
import data.local.room.YouDo2Database
import data.local.room.getYouDo2Database
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import presentation.dashboard.helpers.DashboardViewModel
import presentation.login.helpers.LoginViewModel
import presentation.projects.helpers.ProjectsViewModel
import presentation.signup.helpers.SignupViewModel
import presentation.onboarding.helpers.OnBoardingViewModel

actual val platformModule= module {
    single<YouDo2Database> { getYouDo2Database(get()) }
    viewModelOf(::LoginViewModel)
    viewModelOf(::SignupViewModel)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::AppViewModel)
    viewModelOf(::ProjectsViewModel)
    viewModelOf(::OnBoardingViewModel)
    single<DataStore<Preferences>> { createDataStore(androidContext()) }
}