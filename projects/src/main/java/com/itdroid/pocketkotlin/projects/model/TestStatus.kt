package com.itdroid.pocketkotlin.projects.model

/**
 * @author itdroid
 */
enum class TestStatus {

    Ok,
    Fail;

    fun isOk() = this == Ok

}

internal fun ApiTestStatus.fromApiTestStatus(): TestStatus =
    when (this) {
        ApiTestStatus.Ok -> TestStatus.Ok
        ApiTestStatus.Fail -> TestStatus.Fail
    }
