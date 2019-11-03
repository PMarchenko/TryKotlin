package com.pmarchenko.itdroid.pocketkotlin

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.kotlin

/**
 * @author Pavel Marchenko
 */
object Versions {

    const val compileSdk = 29
    const val minSdk = 21
    const val targetSdk = 29

    const val buildTools = "29.0.2"

    const val versionCode = 1
    const val versionName = "1.0"

    const val junit = "4.12"
    const val androidxJunit = "1.1.1"
    const val espressoCore = "3.2.0"

}

val DependencyHandler.kotlinStdLib
    get() = kotlin("stdlib", "1.3.50")