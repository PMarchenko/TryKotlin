package com.pmarchenko.itdroid.pocketkotlin.preferences

import androidx.annotation.StringRes
import com.google.gson.annotations.SerializedName

/**
 * @author Pavel Marchenko
 */
enum class AppThemePreference(@StringRes val title: Int) {

    @SerializedName("atp_l")
    Light(R.string.settings__value__app_theme_light),
    @SerializedName("atp_d")
    Dark(R.string.settings__value__app_theme_dark),
    @SerializedName("atp_a")
    Auto(R.string.settings__value__app_theme_auto);
}