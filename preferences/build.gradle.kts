import com.itdroid.pocketkotlin.AppComponents
import com.itdroid.pocketkotlin.AppVersion
import com.itdroid.pocketkotlin.Dependencies.AndroidX
import com.itdroid.pocketkotlin.Dependencies.Module
import com.itdroid.pocketkotlin.Dependencies.Tests
import com.itdroid.pocketkotlin.Dependencies.jarLibs
import com.itdroid.pocketkotlin.Dependencies.kotlinStdLib
import com.itdroid.pocketkotlin.Dependencies.Google

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {

    compileSdkVersion(AppComponents.AndroidSdk.compileSdk)
    buildToolsVersion(AppComponents.AndroidSdk.buildTools)

    defaultConfig {
        versionCode = AppVersion.code
        versionName = AppVersion.name

        minSdkVersion(AppComponents.AndroidSdk.minSdk)
        targetSdkVersion(AppComponents.AndroidSdk.targetSdk)

        testInstrumentationRunner = Tests.testRunner

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = AppComponents.CompileOptions.sourceCompatibility
        targetCompatibility = AppComponents.CompileOptions.targetCompatibility
    }

    kotlinOptions {
        jvmTarget = AppComponents.KotlinOptions.jvmTarget
    }
}

dependencies {
    implementation(jarLibs)
    implementation(kotlinStdLib)

    implementation(project(Module.utils))

    implementation(AndroidX.preferences)

    implementation(Google.gson)
}