package di

import data.local.room.YouDo2Database
import data.local.room.getYouDo2Database
import org.koin.dsl.module

actual val platformModule= module {
    single<YouDo2Database> { getYouDo2Database() }
}