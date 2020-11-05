package com.itdroid.pocketkotlin.utils

/**
 * @author itdroid
 */
open class UiAction<T>(
    val title: TextInput,
    val onClick: (T) -> Unit,
)