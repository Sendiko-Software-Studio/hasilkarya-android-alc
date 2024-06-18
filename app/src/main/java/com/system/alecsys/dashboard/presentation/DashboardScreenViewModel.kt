package com.system.alecsys.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.alecsys.core.repositories.user.UserRepository
import com.system.alecsys.core.ui.utils.FailedRequest
import com.system.alecsys.dashboard.data.CheckTokenResponse
import com.system.alecsys.login.data.LoginRequest
import com.system.alecsys.login.data.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DashboardScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardScreenState())
    private val _token = userRepository.getToken()
    private val _name = userRepository.getName()
    private val _email = userRepository.getEmail()
    private val _password = userRepository.getPassword()

    private val _userEmailPass =
        combine(_name, _email, _password, _state) { name, email, password, state ->
            state.copy(
                name = name,
                email = email,
                password = password
            )
        }

    private val _userState =
        combine(_token, _userEmailPass, _state) { token, userEmailPass, state ->
            state.copy(
                token = token,
                name = userEmailPass.name,
                email = userEmailPass.email,
                password = userEmailPass.password
            )
        }

    val state = combine(_userState, _state) { userState, state ->
        state.copy(
            token = userState.token,
            name = userState.name,
            role = userState.role,
            email = userState.email,
            password = userState.password,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), DashboardScreenState())

    init {
        viewModelScope.launch {
            _token.collect { token ->
                checkToken(token = "Bearer $token")
            }
        }
    }

    // checking token
    private fun checkToken(token: String) {
        _state.update { it.copy(isConnecting = true) }
        val request = userRepository.checkToken(token)
        request.enqueue(
            object : Callback<CheckTokenResponse> {
                override fun onResponse(
                    call: Call<CheckTokenResponse>,
                    response: Response<CheckTokenResponse>
                ) {
                    _state.update { it.copy(isConnecting = false) }
                    when (response.code()) {
                        401 -> {
                            login(
                                email = state.value.email,
                                password = state.value.password,
                            )
                        }

                        200 -> {
                            _state.update {
                                it.copy(isTokenExpired = false)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<CheckTokenResponse>, t: Throwable) {
                    _state.update { it.copy(isConnecting = false) }
                }

            }
        )
    }

    private fun login(email: String, password: String) {
        _state.update { it.copy(isConnecting = true) }
        val data = LoginRequest(
            email = email,
            password = password
        )
        val request = userRepository.login(data)
        request.enqueue(
            object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    _state.update { it.copy(isConnecting = false) }
                    if (response.code() != 200) {
                        _state.update { it.copy(notificationMessage = "Maaf, aplikasi tidak bisa terhubung ke server.") }
                    } else {
                        viewModelScope.launch {
                            userRepository.saveToken(response.body()!!.token)
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    _state.update {
                        it.copy(
                            isConnecting = false,
                            notificationMessage = "Maaf, aplikasi tidak bisa terhubung ke server."
                        )
                    }
                }

            }
        )
    }

    private fun retryLogin() {
        viewModelScope.launch {
            login(state.value.email, state.value.password)
        }
    }

    private fun clearNotificationState() {
        _state.update { it.copy(isRequestFailed = FailedRequest()) }
    }

    // onEvent
    fun onEvent(event: DashboardScreenEvent) {
        when (event) {
            DashboardScreenEvent.ClearNotificationState -> clearNotificationState()

            DashboardScreenEvent.RetryLogin -> retryLogin()

            is DashboardScreenEvent.CheckToken -> checkToken(state.value.token)
        }
    }
}