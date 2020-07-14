package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */
enum class TestStatus { OK, FAIL }

internal fun ApiTestStatus.fromApiTestStatus(): TestStatus =
    when (this) {
        ApiTestStatus.OK -> TestStatus.OK
        ApiTestStatus.FAIL -> TestStatus.FAIL
    }
