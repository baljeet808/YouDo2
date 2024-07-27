package presentation.dashboard

sealed class DashboardScreenEvents {
    data object OnAttemptToLogout : DashboardScreenEvents()
    data object OnRefreshUIState : DashboardScreenEvents()
}