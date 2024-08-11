package di

import data.local.room.YouDo2Database
import data.local.room.getYouDo2Database
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import presentation.signup.helpers.SignupViewModel
import presentation.dashboard.helpers.DashboardViewModel
import presentation.login.helpers.LoginViewModel


actual val platformModule= module {
    single<YouDo2Database> { getYouDo2Database() }
    singleOf(::LoginViewModel)
    singleOf(::SignupViewModel)
    singleOf(::DashboardViewModel)
}