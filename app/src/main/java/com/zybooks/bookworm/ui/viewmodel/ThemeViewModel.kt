package com.zybooks.bookworm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeViewModel : ViewModel() {

    private val _isDarkTheme = MutableStateFlow(false) // Default to light mode
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    fun setDarkTheme(enabled: Boolean) {
        _isDarkTheme.value = enabled
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ThemeViewModel()
            }
        }
    }
}
