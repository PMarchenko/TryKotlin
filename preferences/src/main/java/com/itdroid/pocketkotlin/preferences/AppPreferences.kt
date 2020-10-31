package com.itdroid.pocketkotlin.preferences

import android.content.Context
import kotlinx.coroutines.CoroutineScope

class AppPreferences(context: Context, coroutineScope: CoroutineScope) {

    private val storage: Storage = DefaultSharedPreferencesStorage(context, coroutineScope)

    val appTheme: AppPreference<AppThemePreference> =
        AppPreferenceImpl(APP_THEME, AppThemePreference.Auto, storage, enumConverter())

    val easterEggDialogShown: AppPreference<Boolean> =
        AppPreferenceImpl(SETTINGS_EASTER_EGG_DIALOG_SHOWN, false, storage)

    val logsPanelWeight: AppPreference<Float> =
        AppPreferenceImpl(
            LOGS_PANEL_WEIGHT,
            LogsPanelWeightPreference.DEFAULT,
            storage,
            filter = LogsPanelWeightPreference.filter
        )

    val showLogsPanel: AppPreference<Boolean> =
        AppPreferenceImpl(
            SHOW_LOGS_PANNEL,
            true,
            storage,
        )

    fun release() {
        storage.release()
    }

    @Suppress("SpellCheckingInspection")
    private companion object {
        // pattern - (a)pp(p)references_(d)efault(s)hared(p)references(s)torage_(t)ype_(n)ame
        const val APP_THEME = "ap_dsps_s_at"
        const val SETTINGS_EASTER_EGG_DIALOG_SHOWN = "ap_dsps_b_seeds"
        const val LOGS_PANEL_WEIGHT = "ap_dsps_f_lpw"
        const val SHOW_LOGS_PANNEL = "ap_dsps_b_slp"
    }
}