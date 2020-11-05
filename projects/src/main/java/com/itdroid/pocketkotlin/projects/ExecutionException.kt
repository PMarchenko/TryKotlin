package com.itdroid.pocketkotlin.projects

import java.lang.RuntimeException

/**
 * @author itdroid
 */
class ExecutionException(val code: Int, message: String) : RuntimeException(message)