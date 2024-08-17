package di

import data.repository_implementations.AuthRepositoryImpl
import data.repository_implementations.ColorPaletteRepositoryImpl
import data.repository_implementations.DatabaseOperationsRepositoryImpl
import data.repository_implementations.TaskRepositoryImpl
import data.repository_implementations.InvitationsRepositoryImpl
import data.repository_implementations.MessageRepositoryImpl
import data.repository_implementations.NotificationRepositoryImpl
import data.repository_implementations.ProjectRepositoryImpl
import data.repository_implementations.UserRepositoryImpl
import domain.repository_interfaces.AuthRepository
import domain.repository_interfaces.ColorPaletteRepository
import domain.repository_interfaces.DataStoreRepository
import domain.repository_interfaces.DatabaseOperationsRepository
import domain.repository_interfaces.TaskRepository
import domain.repository_interfaces.InvitationsRepository
import domain.repository_interfaces.MessageRepository
import domain.repository_interfaces.NotificationRepository
import domain.repository_interfaces.ProjectRepository
import domain.repository_interfaces.UserRepository
import domain.use_cases.auth_use_cases.GetCurrentUserIdUseCase
import domain.use_cases.auth_use_cases.GetCurrentUserUseCase
import domain.use_cases.auth_use_cases.IsUserAuthenticatedUseCase
import domain.use_cases.auth_use_cases.LoginUseCase
import domain.use_cases.auth_use_cases.SignOutUseCase
import domain.use_cases.auth_use_cases.SignupUseCase
import domain.use_cases.auth_use_cases.UpdateCurrentUserUseCase
import domain.use_cases.database_operations_use_cases.DeleteAllTablesUseCase
import domain.use_cases.task_use_cases.DeleteTaskUseCase
import domain.use_cases.task_use_cases.DeleteTasksByProjectIdUseCase
import domain.use_cases.task_use_cases.GetAllOtherTasksUseCase
import domain.use_cases.task_use_cases.GetAllTasksUseCase
import domain.use_cases.task_use_cases.GetAllTasksWithProjectAsFlowUseCase
import domain.use_cases.task_use_cases.GetAllTasksWithProjectUseCase
import domain.use_cases.task_use_cases.GetTaskByIdUseCase
import domain.use_cases.task_use_cases.GetPendingTasksUseCase
import domain.use_cases.task_use_cases.GetProjectTasksAsFlowUseCase
import domain.use_cases.task_use_cases.GetProjectTasksUseCase
import domain.use_cases.task_use_cases.GetTaskByIdAsFlowUseCase
import domain.use_cases.task_use_cases.GetTodayTasksUseCase
import domain.use_cases.task_use_cases.GetTomorrowTasksUseCase
import domain.use_cases.task_use_cases.GetYesterdayTasksUseCase
import domain.use_cases.task_use_cases.UpsertTasksUseCase
import domain.use_cases.invitation_use_cases.DeleteAllInvitationsByProjectIdUseCase
import domain.use_cases.invitation_use_cases.DeleteInvitationUseCase
import domain.use_cases.invitation_use_cases.GetAllInvitationsByProjectIdUseCase
import domain.use_cases.invitation_use_cases.GetAllInvitationsUseCase
import domain.use_cases.invitation_use_cases.GetInvitationByIdAsFlowUseCase
import domain.use_cases.invitation_use_cases.GetInvitationByIdUseCase
import domain.use_cases.invitation_use_cases.UpsertAllInvitationsUseCase
import domain.use_cases.message_use_cases.DeleteAllMessagesOfProjectUseCase
import domain.use_cases.message_use_cases.DeleteMessageUseCase
import domain.use_cases.message_use_cases.GetAllMessagesAsFlowUseCase
import domain.use_cases.message_use_cases.GetAllMessagesByProjectIDAsFlowUseCase
import domain.use_cases.message_use_cases.GetAllMessagesByProjectIDUseCase
import domain.use_cases.message_use_cases.GetAllMessagesUseCase
import domain.use_cases.message_use_cases.GetMessageByIdAsFlowUseCase
import domain.use_cases.message_use_cases.GetMessageByIdUseCase
import domain.use_cases.message_use_cases.GetPagedMessagesOfProject
import domain.use_cases.message_use_cases.UpsertMessagesUseCase
import domain.use_cases.notification_use_cases.DeleteAllNotificationsUseCase
import domain.use_cases.notification_use_cases.DeleteNotificationUseCase
import domain.use_cases.notification_use_cases.GetAllNotificationsAsFlowUseCase
import domain.use_cases.notification_use_cases.GetNotificationByIdUseCase
import domain.use_cases.notification_use_cases.GetNotificationsByIdAsFlowUseCase
import domain.use_cases.notification_use_cases.UpsertNotificationsUseCase
import domain.use_cases.palette_use_cases.DeletePaletteUseCase
import domain.use_cases.palette_use_cases.GetAllPalettesAsFlowUseCase
import domain.use_cases.palette_use_cases.GetAllPalettesUseCase
import domain.use_cases.palette_use_cases.GetColorPaletteByIdUseCase
import domain.use_cases.palette_use_cases.UpsertPalettesUseCase
import domain.use_cases.project_use_cases.DeleteProjectUseCase
import domain.use_cases.project_use_cases.GetAllProjectsAsFlowUseCase
import domain.use_cases.project_use_cases.GetProjectByIdAsFlowUseCase
import domain.use_cases.project_use_cases.GetProjectByIdUseCase
import domain.use_cases.project_use_cases.GetProjectsUseCase
import domain.use_cases.project_use_cases.GetProjectsWithDoToosUseCase
import domain.use_cases.project_use_cases.SearchProjectsUseCase
import domain.use_cases.project_use_cases.UpsertProjectUseCase
import domain.use_cases.user_use_cases.GetUserByIdAsFlowUseCase
import domain.use_cases.user_use_cases.GetUserByIdUseCase
import domain.use_cases.user_use_cases.GetUsersByIdsUseCase
import domain.use_cases.user_use_cases.GetUsersUseCase
import domain.use_cases.user_use_cases.UpsertUserUseCase
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


val repositoriesModule = module {
    single<ProjectRepository>{ ProjectRepositoryImpl(get())}
    single<UserRepository>{ UserRepositoryImpl(get())}
    single<NotificationRepository>{ NotificationRepositoryImpl(get())}
    single<MessageRepository>{ MessageRepositoryImpl(get())}
    single<InvitationsRepository>{ InvitationsRepositoryImpl(get())}
    single<TaskRepository>{ TaskRepositoryImpl(get())}
    single<DatabaseOperationsRepository>{ DatabaseOperationsRepositoryImpl(get())}
    single<ColorPaletteRepository>{ ColorPaletteRepositoryImpl(get())}
    single<AuthRepository>{ AuthRepositoryImpl()}
    single<DataStoreRepository>{ DataStoreRepository(get())}
}

val authUseCasesModule = module {
    single<UpdateCurrentUserUseCase>{ UpdateCurrentUserUseCase(get()) }
    single<GetCurrentUserUseCase>{ GetCurrentUserUseCase(get()) }
    single<GetCurrentUserIdUseCase>{ GetCurrentUserIdUseCase(get())}
    single<IsUserAuthenticatedUseCase>{ IsUserAuthenticatedUseCase(get())}
    single<LoginUseCase>{ LoginUseCase(get())}
    single<SignOutUseCase>{ SignOutUseCase(get())}
    single<SignupUseCase>{ SignupUseCase(get())}
}

val projectUseCasesModule = module {
    single<UpsertProjectUseCase>{ UpsertProjectUseCase(get())}
    single<SearchProjectsUseCase>{ SearchProjectsUseCase(get())}
    single<GetProjectsWithDoToosUseCase>{ GetProjectsWithDoToosUseCase(get())}
    single<GetProjectsUseCase>{ GetProjectsUseCase(get())}
    single<GetProjectByIdUseCase>{ GetProjectByIdUseCase(get())}
    single<GetProjectByIdAsFlowUseCase>{ GetProjectByIdAsFlowUseCase(get())}
    single<GetAllProjectsAsFlowUseCase>{ GetAllProjectsAsFlowUseCase(get())}
    single<DeleteProjectUseCase>{ DeleteProjectUseCase(get())}
}
val usersUseCasesModule = module {
    single<UpsertUserUseCase>{ UpsertUserUseCase(get())}
    single<GetUsersUseCase>{ GetUsersUseCase(get())}
    single<GetUsersByIdsUseCase>{ GetUsersByIdsUseCase(get())}
    single<GetUserByIdUseCase>{ GetUserByIdUseCase(get())}
    single<GetUserByIdAsFlowUseCase>{ GetUserByIdAsFlowUseCase(get())}
}

val palettesUseCasesModule = module {
    single<UpsertPalettesUseCase>{ UpsertPalettesUseCase(get())}
    single<GetColorPaletteByIdUseCase>{ GetColorPaletteByIdUseCase(get())}
    single<GetAllPalettesUseCase>{ GetAllPalettesUseCase(get())}
    single<GetAllPalettesAsFlowUseCase>{ GetAllPalettesAsFlowUseCase(get())}
    single<DeletePaletteUseCase>{ DeletePaletteUseCase(get())}
}

val notificationsUseCasesModule = module {
    single<UpsertNotificationsUseCase>{ UpsertNotificationsUseCase(get())}
    single<GetNotificationByIdUseCase>{ GetNotificationByIdUseCase(get())}
    single<GetNotificationsByIdAsFlowUseCase>{ GetNotificationsByIdAsFlowUseCase(get())}
    single<GetAllNotificationsAsFlowUseCase>{ GetAllNotificationsAsFlowUseCase(get())}
    single<DeleteNotificationUseCase>{ DeleteNotificationUseCase(get())}
    single<DeleteAllNotificationsUseCase>{ DeleteAllNotificationsUseCase(get())}
}

val messagesUseCasesModule = module {
    single<UpsertMessagesUseCase>{ UpsertMessagesUseCase(get())}
    single<GetPagedMessagesOfProject>{ GetPagedMessagesOfProject(get())}
    single<GetMessageByIdUseCase>{ GetMessageByIdUseCase(get())}
    single<GetMessageByIdAsFlowUseCase>{ GetMessageByIdAsFlowUseCase(get())}
    single<GetAllMessagesUseCase>{ GetAllMessagesUseCase(get())}
    single<GetAllMessagesByProjectIDAsFlowUseCase>{ GetAllMessagesByProjectIDAsFlowUseCase(get())}
    single<GetAllMessagesByProjectIDUseCase>{ GetAllMessagesByProjectIDUseCase(get())}
    single<GetAllMessagesAsFlowUseCase>{ GetAllMessagesAsFlowUseCase(get())}
    single<DeleteAllMessagesOfProjectUseCase>{ DeleteAllMessagesOfProjectUseCase(get())}
    single<DeleteMessageUseCase>{ DeleteMessageUseCase(get())}
}

val invitationsUseCasesModule = module {
    single<UpsertAllInvitationsUseCase>{ UpsertAllInvitationsUseCase(get())}
    single<GetInvitationByIdUseCase>{ GetInvitationByIdUseCase(get())}
    single<GetInvitationByIdAsFlowUseCase>{ GetInvitationByIdAsFlowUseCase(get())}
    single<GetAllInvitationsUseCase>{ GetAllInvitationsUseCase(get())}
    single<GetAllInvitationsByProjectIdUseCase>{ GetAllInvitationsByProjectIdUseCase(get())}
    single<DeleteInvitationUseCase>{ DeleteInvitationUseCase(get())}
    single<DeleteAllInvitationsByProjectIdUseCase>{ DeleteAllInvitationsByProjectIdUseCase(get())}
}

val tasksUseCasesModule = module {
    single<UpsertTasksUseCase>{ UpsertTasksUseCase(get())}
    single<GetYesterdayTasksUseCase>{ GetYesterdayTasksUseCase(get())}
    single<GetTomorrowTasksUseCase>{ GetTomorrowTasksUseCase(get())}
    single<GetTodayTasksUseCase>{ GetTodayTasksUseCase(get())}
    single<GetTaskByIdAsFlowUseCase>{ GetTaskByIdAsFlowUseCase(get())}
    single<GetProjectTasksUseCase>{ GetProjectTasksUseCase(get())}
    single<GetProjectTasksAsFlowUseCase>{ GetProjectTasksAsFlowUseCase(get())}
    single<GetPendingTasksUseCase>{ GetPendingTasksUseCase(get())}
    single<GetTaskByIdUseCase>{ GetTaskByIdUseCase(get())}
    single<GetAllTasksUseCase>{ GetAllTasksUseCase(get())}
    single<GetAllTasksWithProjectUseCase>{ GetAllTasksWithProjectUseCase(get())}
    single<GetAllTasksWithProjectAsFlowUseCase>{ GetAllTasksWithProjectAsFlowUseCase(get())}
    single<GetAllOtherTasksUseCase>{ GetAllOtherTasksUseCase(get())}
    single<DeleteTasksByProjectIdUseCase>{ DeleteTasksByProjectIdUseCase(get())}
    single<DeleteTaskUseCase>{ DeleteTaskUseCase(get())}
}

val databaseOperationsCasesModule = module {
    single<DeleteAllTablesUseCase>{ DeleteAllTablesUseCase(get())}
}

expect val platformModule: Module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            platformModule,
            repositoriesModule,
            projectUseCasesModule,
            usersUseCasesModule,
            palettesUseCasesModule,
            notificationsUseCasesModule,
            messagesUseCasesModule,
            invitationsUseCasesModule,
            tasksUseCasesModule,
            databaseOperationsCasesModule,
            authUseCasesModule
        )
    }

