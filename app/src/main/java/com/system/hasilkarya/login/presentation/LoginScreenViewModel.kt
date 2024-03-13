package com.system.hasilkarya.login.presentation

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.hasilkarya.core.preferences.AppPreferences
import com.system.hasilkarya.core.ui.utils.ErrorTextField
import com.system.hasilkarya.core.ui.utils.FailedRequest
import com.system.hasilkarya.login.data.LoginRequest
import com.system.hasilkarya.login.data.LoginResponse
import com.system.hasilkarya.login.domain.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val preferences: AppPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(LoginScreenState())
    val state = _state.asStateFlow()

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun isDataValid(): Boolean {
        if (!isValidEmail(state.value.emailText)){
            _state.update {
                it.copy(emailErrorState = ErrorTextField(isError = true, errorMessage = "Mohon gunakan email yang valid."))
            }
            return false
        }
        if (state.value.passwordText.isBlank()) {
            _state.update {
                it.copy(passwordErrorState = ErrorTextField(isError = true, errorMessage = "Mohon isi password."))
            }
            return false
        }
        return true
    }

    private fun login() {
        _state.update { it.copy(isLoading = true) }
        val data = LoginRequest(
            email = state.value.emailText,
            password = state.value.passwordText
        )
        val request = repository.login(data)
        request.enqueue(
            object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    _state.update { it.copy(isLoading = false) }
                    when (response.code()) {
                        201 -> viewModelScope.launch {
                            preferences.setName(response.body()!!.user.checker.name)
                            preferences.setToken(response.body()!!.token)
                            _state.update {
                                it.copy(isLoginSuccessful = true)
                            }
                        }

                        404 -> _state.update {
                            it.copy(
                                isRequestFailed = FailedRequest(isFailed = true),
                                notificationMessage = "Mohon cek kembali email dan password."
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    _state.update { it.copy(isLoading = false) }
                }

            }
        )
    }

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.OnEmailChange -> _state.update {
                it.copy(emailText = event.email, emailErrorState = ErrorTextField())
            }

            LoginScreenEvent.OnEmailClear -> _state.update {
                it.copy(emailText = "")
            }

            is LoginScreenEvent.OnPasswordChange -> _state.update {
                it.copy(passwordText = event.password, passwordErrorState = ErrorTextField())
            }

            LoginScreenEvent.OnPasswordClear -> _state.update {
                it.copy(passwordText = "")
            }

            is LoginScreenEvent.OnPasswordVisibilityChange -> _state.update {
                it.copy(isPasswordVisible = event.isVisible)
            }

            LoginScreenEvent.OnClearNotification -> _state.update {
                it.copy(notificationMessage = "")
            }

            LoginScreenEvent.OnLoginClick -> if (isDataValid()) login()
        }
    }
}