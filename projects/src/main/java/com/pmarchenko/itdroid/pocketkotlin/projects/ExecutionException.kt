package com.pmarchenko.itdroid.pocketkotlin.projects

import java.lang.RuntimeException

/**
 * @author Pavel Marchenko
 */
class ExecutionException(val code: Int, message: String) : RuntimeException(message)