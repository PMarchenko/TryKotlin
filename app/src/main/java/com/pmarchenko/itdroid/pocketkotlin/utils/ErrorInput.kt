package com.pmarchenko.itdroid.pocketkotlin.utils

/**
 * @author Pavel Marchenko
 */
data class ErrorInput(
    val resId: Int = -1,
    val error: TextInput = TextInput(resId),
    val cause: Throwable? = null,
)