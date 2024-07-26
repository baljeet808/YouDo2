package di

import data.local.room.YouDo2Database
import data.local.room.getYouDo2Database
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import presentation.login.LoginViewModel
import presentation.signup.SignupViewModel

actual val platformModule= module {
    single<YouDo2Database> { getYouDo2Database(get()) }
    viewModelOf(::LoginViewModel)
    viewModelOf(::SignupViewModel)
}