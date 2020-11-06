package com.itdroid.pocketkotlin

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.kotlin.dsl.kotlin

/**
 * @author itdroid
 */
object Dependencies {

    val Project.jarLibs: ConfigurableFileTree
        get() = fileTree(mapOf("dir" to "libs", "include" to "*.jar"))

    @Suppress("MemberVisibilityCanBePrivate")
    const val kotlinVersion = "1.4.10"

    val DependencyHandler.kotlinStdLib
        get() = kotlin("stdlib", kotlinVersion)

    object Tests {

        const val junit = "junit:junit:4.12"
        const val androidxJunit = "androidx.test.ext:junit:1.1.1"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"

        const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    object Module {

        const val app = ":app"
        const val projects = ":projects"
        const val syntax = ":syntax"
        const val utils = ":utils"
        const val database = ":database"
        const val network = ":network"
        const val preferences = ":preferences"
        const val parserKotlinLang = ":parser:kotlinLang"
    }

    object DebugTools {

        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.5"


        private const val stethoVersion = "1.5.1"
        const val stetho = "com.facebook.stetho:stetho:$stethoVersion"
        const val stethoOkHttp = "com.facebook.stetho:stetho-okhttp3:$stethoVersion"
    }

    object KotlinX {

        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1"
    }

    object AndroidX {

        const val coreKtx = "androidx.core:core-ktx:1.3.2"

        const val appCompat = "androidx.appcompat:appcompat:1.3.0-alpha02"

        const val activityKtx = "androidx.activity:activity-ktx:1.1.0"

        private const val lifecycleVersion = "2.2.0"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
        const val viewModelSaveState =
            "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion"

        private const val roomVersion = "2.2.5"
        const val room = "androidx.room:room-ktx:$roomVersion"
        const val roomCompiler = "androidx.room:room-compiler:$roomVersion"

        private const val prefsVersion = "1.1.1"
        const val preferences = "androidx.preference:preference-ktx:$prefsVersion"

        private const val initializerVersion = "1.0.0"
        const val initializer = "androidx.startup:startup-runtime:$initializerVersion"

        object Compose {

            @Suppress("MemberVisibilityCanBePrivate")
            const val version = "1.0.0-alpha06"

            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val uiTooling = "androidx.ui:ui-tooling:$version"
            const val material = "androidx.compose.material:material:$version"
            const val liveData = "androidx.compose.runtime:runtime-livedata:$version"
        }
    }

    object Google {

        const val material = "com.google.android.material:material:1.2.1"

        const val gson = "com.google.code.gson:gson:2.8.6"
    }

    object GooglePlay {

        const val ossLicenses = "com.google.android.gms:play-services-oss-licenses:17.0.0"
    }

    object Network {
        private const val retorfitVersion = "2.9.0"
        const val retorfit = "com.squareup.retrofit2:retrofit:$retorfitVersion"
        const val retorfitGsonConverter = "com.squareup.retrofit2:converter-gson:$retorfitVersion"
    }

    object Logger {
        const val timber = "com.jakewharton.timber:timber:4.7.1"
    }
}
