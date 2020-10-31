package com.itdroid.pocketkotlin.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.itdroid.pocketkotlin.preferences.AppPreferences
import com.itdroid.pocketkotlin.preferences.AppThemePreference

/**
 * @author itdroid
 */
class AppPreferencesViewMolder(app: Application) : AndroidViewModel(app) {

    private val prefs = AppPreferences(app, viewModelScope)

    var appTheme: AppThemePreference by prefs.appTheme
    val appThemeObservable: LiveData<AppThemePreference> = prefs.appTheme.liveData()

    var easterEggDialogShown: Boolean by prefs.easterEggDialogShown
    val easterEggDialogShownObservable: LiveData<Boolean> = prefs.easterEggDialogShown.liveData()

    var logsPanelWeight: Float by prefs.logsPanelWeight
    val logsPanelWeightObservable: LiveData<Float> = prefs.logsPanelWeight.liveData()

    var showLogsPanel: Boolean by prefs.showLogsPanel
    val showLogsPanelObservable: LiveData<Boolean> = prefs.showLogsPanel.liveData()

    override fun onCleared() {
        super.onCleared()
        prefs.release()
    }
}