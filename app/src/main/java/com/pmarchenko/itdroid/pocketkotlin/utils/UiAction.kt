package com.pmarchenko.itdroid.pocketkotlin.utils

/**
 * @author Pavel Marchenko
 */
open class UiAction<T>(
    val title: TextInput,
    val onClick: (T) -> Unit,
)