package com.system.alecsys.core.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.system.alecsys.core.ui.theme.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val tokenKey = stringPreferencesKey("token")
    private val nameKey = stringPreferencesKey("name")
    private val userIdKey = stringPreferencesKey("user_id")
    private val emailKey = stringPreferencesKey("email")
    private val passwordKey = stringPreferencesKey("pass")
    private val roleKey = stringPreferencesKey("role")
    private val themeKey = stringPreferencesKey(AppTheme.Default.name)

    suspend fun setPassword(password: String) {
        dataStore.edit {
            it[passwordKey] = password
        }
    }

    fun getPassword(): Flow<String> {
        return dataStore.data.map {
            it[passwordKey]?:""
        }
    }

    suspend fun setTheme(theme: AppTheme) {
        dataStore.edit {
            it[themeKey] = theme.name
        }
    }

    fun getTheme(): Flow<AppTheme> {
        return dataStore.data.map {
            if (it[themeKey]?.isNotBlank() == true){
                enumValueOf(it[themeKey]!!)
            } else AppTheme.Default
        }
    }

    suspend fun setRole(role: String) {
        dataStore.edit {
            it[roleKey] = role
        }
    }

    fun getRole(): Flow<String> {
        return dataStore.data.map {
            it[roleKey]?:""
        }
    }

    suspend fun setEmail(email: String) {
        dataStore.edit {
            it[emailKey] = email
        }
    }

    fun getEmail(): Flow<String> {
        return dataStore.data.map {
            it[emailKey]?:""
        }
    }

    suspend fun clearData() {
        dataStore.edit {
            it[tokenKey] = ""
            it[nameKey] = ""
            it[userIdKey] = ""
            it[emailKey] = ""
            it[roleKey] = ""
        }
    }

    suspend fun setToken(token: String) {
        dataStore.edit {
            it[tokenKey] = token
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map {
            it[tokenKey]?:""
        }
    }

    suspend fun setName(name: String) {
        dataStore.edit {
            it[nameKey] = name
        }
    }

    fun getName(): Flow<String> {
        return dataStore.data.map {
            it[nameKey]?:""
        }
    }

    suspend fun setUserId(userId: String) {
        dataStore.edit {
            it[this.userIdKey] = userId
        }
    }

    fun getUserId(): Flow<String> {
        return dataStore.data.map {
            it[userIdKey]?:""
        }
    }
}