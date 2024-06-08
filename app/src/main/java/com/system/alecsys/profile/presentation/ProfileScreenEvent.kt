package com.system.alecsys.profile.presentation

import com.system.alecsys.core.ui.theme.AppTheme


sealed class ProfileScreenEvent {
    data object OnLogout: ProfileScreenEvent()
    data class OnThemeChanged(val theme: AppTheme): ProfileScreenEvent()
}
