package com.robjonesdev.todoprogger.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robjonesdev.todoprogger.presentation.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    // For now, we'll keep the theme in memory. 
    // In a real app, you'd persist this using DataStore.
    private val _selectedTheme = MutableStateFlow(AppTheme.Green)
    val selectedTheme: StateFlow<AppTheme> = _selectedTheme

    fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            _selectedTheme.value = theme
        }
    }
}
