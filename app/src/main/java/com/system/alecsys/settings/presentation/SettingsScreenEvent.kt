package com.system.alecsys.settings.presentation

import com.system.alecsys.core.ui.theme.AppTheme


sealed class SettingsScreenEvent {
    data object OnLogout: SettingsScreenEvent()
    data class OnThemeChanged(val theme: AppTheme): SettingsScreenEvent()
}
