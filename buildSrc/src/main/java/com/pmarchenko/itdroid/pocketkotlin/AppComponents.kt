package com.pmarchenko.itdroid.pocketkotlin

import org.gradle.api.JavaVersion

/**
 * @author Pavel Marchenko
 */
object AppComponents {

    object CompileOptions {
        val sourceCompatibility = JavaVersion.VERSION_1_8
        val targetCompatibility = JavaVersion.VERSION_1_8
    }

    object KotlinOptions {
        val jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    object AndroidSdk {

        const val buildTools = "29.0.2"

        const val compileSdk = 29
        const val minSdk = 21
        const val targetSdk = 29
    }
}

