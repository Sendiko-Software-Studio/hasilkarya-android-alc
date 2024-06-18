package com.system.alecsys.core.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.alecsys.core.ui.theme.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(appPreferences: AppPreferences): ViewModel() {

    private val _theme = appPreferences.getTheme()
    private var _state = MutableStateFlow(AppTheme())
    val state = combine(_theme, _state) { theme, state ->
        state.copy(theme = theme)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppTheme())

}

data class AppTheme(
    val theme: AppTheme = AppTheme.Default
)