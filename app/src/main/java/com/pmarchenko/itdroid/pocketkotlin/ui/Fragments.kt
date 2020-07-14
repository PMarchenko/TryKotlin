package com.pmarchenko.itdroid.pocketkotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * @author Pavel Marchenko
 */
inline fun <T : Fragment> T.withArgs(provider: Bundle.() -> Unit): T {
    val args = arguments ?: Bundle().also { arguments = it }
    provider(args)
    return this
}

inline fun <T> Fragment.fromArgs(
    key: String,
    defaultValue: () -> T = { error("No argument for '$key'") }
): T {
    val args = arguments
    args!!

    @Suppress("UNCHECKED_CAST")
    return args[key] as? T ?: defaultValue()
}