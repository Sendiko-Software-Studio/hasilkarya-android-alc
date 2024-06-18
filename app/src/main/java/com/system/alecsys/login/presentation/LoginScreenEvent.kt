package com.system.alecsys.login.presentation

sealed class LoginScreenEvent {
    data class OnEmailChange(val email: String): LoginScreenEvent()
    data object OnEmailClear: LoginScreenEvent()
    data class OnPasswordChange(val password: String): LoginScreenEvent()
    data object OnPasswordClear: LoginScreenEvent()
    data class OnPasswordVisibilityChange(val isVisible: Boolean): LoginScreenEvent()
    data object OnClearNotification: LoginScreenEvent()
    data object OnLoginClick: LoginScreenEvent()
}
