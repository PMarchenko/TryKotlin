import com.pmarchenko.itdroid.pocketkotlin.AppComponents
import com.pmarchenko.itdroid.pocketkotlin.AppVersion
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.KotlinX
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.DebugTools
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.Tests
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.AndroidX
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.kotlinStdLib
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.jarLibs

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

    implementation(KotlinX.coroutines)

    implementation(AndroidX.appCompat)
    implementation(AndroidX.lifecycleViewModelKtx)
    implementation(AndroidX.lifecycleExt)

    implementation(DebugTools.logger)
}
