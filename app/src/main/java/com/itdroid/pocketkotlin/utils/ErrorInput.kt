package com.itdroid.pocketkotlin.utils

/**
 * @author itdroid
 */
data class ErrorInput(
    val resId: Int = -1,
    val error: TextInput = TextInput(resId),
    val cause: Throwable? = null,
)