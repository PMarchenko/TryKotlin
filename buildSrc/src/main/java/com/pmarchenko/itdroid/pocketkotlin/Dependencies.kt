package com.pmarchenko.itdroid.pocketkotlin

import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.support.delegates.ProjectDelegate

/**
 * @author Pavel Marchenko
 */
object Dependencies {

    val ProjectDelegate.jarLibs: ConfigurableFileTree
        get() = fileTree(mapOf("dir" to "libs", "include" to "*.jar"))


    val DependencyHandler.kotlinStdLib
        get() = kotlin("stdlib", "1.3.50")


    object Tests {

        const val junit = "junit:junit:4.12"
        const val androidxJunit = "androidx.test.ext:junit:1.1.1"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"

        const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    object Modules {

        val DependencyHandler.projectsModule: ProjectDependency
            get() = project(projects)

        val DependencyHandler.databaseModule: ProjectDependency
            get() = project(database)


        val DependencyHandler.networkModule: ProjectDependency
            get() = project(network)

        val DependencyHandler.syntaxModule: ProjectDependency
            get() = project(syntax)

        val DependencyHandler.utilsModule: ProjectDependency
            get() = project(utils)


        const val app = ":app"
        const val projects = ":projects"
        const val syntax = ":syntax"
        const val utils = ":utils"
        const val database = ":database"
        const val network = ":network"
    }

    object DebugTools {

        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.0-alpha-2"
        const val stetho = "com.facebook.stetho:stetho:1.5.1"
        const val stethoOkHttp = "com.facebook.stetho:stetho-okhttp3:1.5.1"
        const val logger = "com.jakewharton.timber:timber:4.7.1"

    }

    object KotlinX {

        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.0"
    }

    object AndroidX {

        const val coreKtx = "androidx.core:core-ktx:1.1.0"

        const val appCompat = "androidx.appcompat:appcompat:1.1.0"
        const val fragments = "androidx.fragment:fragment-ktx:1.2.0-rc03"

        const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val viewPager = "androidx.viewpager2:viewpager2:1.0.0"

        const val navigationFragment = "androidx.navigation:navigation-fragment:2.1.0"
        const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:2.1.0"
        const val navigationUi = "androidx.navigation:navigation-ui:2.1.0"
        const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:2.1.0"

        const val lifecycleExt = "androidx.lifecycle:lifecycle-extensions:2.1.0"
        const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata:2.2.0-rc03"
        const val lifecycleLiveDataCode = "androidx.lifecycle:lifecycle-livedata-core:2.2.0-rc03"
        const val lifecycleExtKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0-rc03"
        const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0-rc03"

        const val room = "androidx.room:room-runtime:2.2.2"
        const val roomKtx = "androidx.room:room-ktx:2.2.2"
        const val roomCompiler = "androidx.room:room-compiler:2.2.2"
    }

    object Google {

        const val material = "com.google.android.material:material:1.2.0-alpha02"
        const val gson = "com.google.code.gson:gson:2.8.6"
    }

    object Network {
        const val retorfit = "com.squareup.retrofit2:retrofit:2.6.0"
        const val retorfitGsonConverter = "com.squareup.retrofit2:converter-gson:2.6.0"
    }
}