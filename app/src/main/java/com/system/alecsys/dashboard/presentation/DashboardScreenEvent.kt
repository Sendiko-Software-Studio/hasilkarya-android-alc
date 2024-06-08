package com.system.alecsys.dashboard.presentation

import com.system.alecsys.core.network.Status

sealed class DashboardScreenEvent {
    data object CheckDataAndPost: DashboardScreenEvent()
    data object ClearNotificationState: DashboardScreenEvent()
    data object RetryLogin: DashboardScreenEvent()
    data class CheckToken(val connectionStatus: Status): DashboardScreenEvent()
}