package com.robjonesdev.todoprogger.presentation.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robjonesdev.todoprogger.presentation.theme.AppTheme
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val themeKey = stringPreferencesKey("app_theme")

    val selectedTheme: StateFlow<AppTheme> = dataStore.data
        .catch {
            emit(emptyPreferences())
        }
        .map { preferences ->
            val themeName = preferences[themeKey] ?: AppTheme.Green.name
            try {
                AppTheme.valueOf(themeName)
            } catch (e: Exception) {
                AppTheme.Green
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = AppTheme.Green
        )

    fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[themeKey] = theme.name
            }
        }
    }
}
