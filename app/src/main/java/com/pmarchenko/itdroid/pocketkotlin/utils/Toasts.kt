package com.pmarchenko.itdroid.pocketkotlin.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ContextAmbient

/**
 * @author Pavel Marchenko
 */
fun Context.toast(@StringRes resId: Int = -1, text: String? = null) {
    Toast.makeText(this, text ?: getString(resId), Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(@StringRes resId: Int = -1, text: String? = null) {
    Toast.makeText(this, text ?: getString(resId), Toast.LENGTH_LONG).show()
}

@Suppress("unused")
@Composable
fun toast(@StringRes resId: Int = -1, text: String? = null) {
    ContextAmbient.current.toast(resId, text)
}

@Suppress("unused")
@Composable
fun toastLong(@StringRes resId: Int = -1, text: String? = null) {
    ContextAmbient.current.toastLong(resId, text)
}