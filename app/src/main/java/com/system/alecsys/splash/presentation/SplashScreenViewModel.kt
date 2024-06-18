package com.system.alecsys.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.alecsys.core.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(preferences: AppPreferences) : ViewModel() {

    private val _state = MutableStateFlow(SplashScreenState())
    private val _token = preferences.getToken()
    val state = combine(flow = _token, flow2 = _state) { token, state ->
        state.copy(token = token)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SplashScreenState())
}