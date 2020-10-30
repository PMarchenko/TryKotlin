package com.pmarchenko.itdroid.pocketkotlin.utils

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * @author Pavel Marchenko
 */
data class TextInput(
    @StringRes private val resId: Int = -1,
    private val text: String? = null,
) {

    @Composable
    fun text(): String = text ?: stringResource(id = resId)
}