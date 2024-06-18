package com.system.alecsys.settings.presentation

import com.system.alecsys.core.ui.theme.AppTheme
import com.system.alecsys.core.ui.utils.FailedRequest

data class SettingsScreenState(
    val name: String = "",
    val email: String = "",
    val isPostSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val isRequestFailed: FailedRequest = FailedRequest(),
    val notificationMessage: String = "",
    val token: String = "",
    val theme: AppTheme = AppTheme.Default
)
