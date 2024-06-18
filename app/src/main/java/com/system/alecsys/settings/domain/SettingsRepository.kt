package com.system.alecsys.settings.domain

import com.system.alecsys.core.network.ApiServices
import com.system.alecsys.core.preferences.AppPreferences
import com.system.alecsys.core.ui.theme.AppTheme
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val preferences: AppPreferences,
    private val apiServices: ApiServices
) {

    fun getName() = preferences.getName()
    fun getToken() = preferences.getToken()
    fun getEmail() = preferences.getEmail()
    fun getTheme() = preferences.getTheme()
    suspend fun setTheme(theme: AppTheme) = preferences.setTheme(theme)
    suspend fun clearData() = preferences.clearData()
    fun logout(token: String) = apiServices.logout(token)
}