package com.system.alecsys.core.repositories.user

import com.system.alecsys.core.network.ApiServices
import com.system.alecsys.core.preferences.AppPreferences
import com.system.alecsys.core.ui.theme.AppTheme
import com.system.alecsys.login.data.LoginRequest
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val preferences: AppPreferences
) {
    fun getToken() = preferences.getToken()
    fun getName() = preferences.getName()
    fun getRole() = preferences.getRole()
    fun getEmail() = preferences.getEmail()
    fun getPassword() = preferences.getPassword()
    suspend fun setPassword(password: String) = preferences.setPassword(password)
    fun getTheme() = preferences.getTheme()
    suspend fun saveToken(token: String) = preferences.setToken(token)
    suspend fun setTheme(theme: AppTheme) = preferences.setTheme(theme)
    suspend fun clearData() = preferences.clearData()
    fun checkToken(token: String) = apiServices.checkToken(token)
    fun login(loginRequest: LoginRequest) = apiServices.login(loginRequest)

}