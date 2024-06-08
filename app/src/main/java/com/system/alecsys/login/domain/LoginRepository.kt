package com.system.alecsys.login.domain

import com.system.alecsys.core.network.ApiServices
import com.system.alecsys.core.preferences.AppPreferences
import com.system.alecsys.login.data.LoginRequest
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val preferences: AppPreferences
) {

    fun login(request: LoginRequest) = apiServices.login(request)
    suspend fun setName(name: String) = preferences.setName(name)
    suspend fun setToken(token: String) = preferences.setToken(token)
    suspend fun setUserId(userId: String) = preferences.setUserId(userId)
    suspend fun setEmail(email: String) = preferences.setEmail(email)
    suspend fun setRole(role: String) = preferences.setRole(role)
    suspend fun setPassword(password: String) = preferences.setPassword(password)
}