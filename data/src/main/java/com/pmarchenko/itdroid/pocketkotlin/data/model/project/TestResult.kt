package com.pmarchenko.itdroid.pocketkotlin.data.model.project

import com.google.gson.annotations.SerializedName

/**
 * @author Pavel Marchenko
 */
data class TestResult(
    val output: String,
    val className: String,
    val methodName: String,
    val executionTime: Long,
    val exception: ProjectException?,
    val status: Status,
    val sourceFileName: String,
    @SerializedName("comparisonFailure")
    val failure: ProjectException?
)

enum class Status { OK, FAIL }