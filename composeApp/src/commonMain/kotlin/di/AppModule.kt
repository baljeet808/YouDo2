package di

import data.repository_implementations.ColorPaletteRepositoryImpl
import data.repository_implementations.DatabaseOperationsRepositoryImpl
import data.repository_implementations.DoTooItemsRepositoryImpl
import data.repository_implementations.InvitationsRepositoryImpl
import data.repository_implementations.MessageRepositoryImpl
import data.repository_implementations.NotificationRepositoryImpl
import data.repository_implementations.ProjectRepositoryImpl
import data.repository_implementations.UserRepositoryImpl
import domain.repository_interfaces.ColorPaletteRepository
import domain.repository_interfaces.DatabaseOperationsRepository
import domain.repository_interfaces.DoTooItemsRepository
import domain.repository_interfaces.InvitationsRepository
import domain.repository_interfaces.MessageRepository
import domain.repository_interfaces.NotificationRepository
import domain.repository_interfaces.ProjectRepository
import domain.repository_interfaces.UserRepository
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


val sharedModule = module {
    single<ProjectRepository>{ ProjectRepositoryImpl(get())}
    single<UserRepository>{ UserRepositoryImpl(get())}
    single<NotificationRepository>{ NotificationRepositoryImpl(get())}
    single<MessageRepository>{ MessageRepositoryImpl(get())}
    single<InvitationsRepository>{ InvitationsRepositoryImpl(get())}
    single<DoTooItemsRepository>{ DoTooItemsRepositoryImpl(get())}
    single<DatabaseOperationsRepository>{ DatabaseOperationsRepositoryImpl(get())}
    single<ColorPaletteRepository>{ ColorPaletteRepositoryImpl(get())}
}

expect val platformModule: Module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            platformModule,
            sharedModule
        )
    }

