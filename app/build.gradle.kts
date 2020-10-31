import com.itdroid.pocketkotlin.AppComponents
import com.itdroid.pocketkotlin.AppVersion
import com.itdroid.pocketkotlin.Dependencies
import com.itdroid.pocketkotlin.Dependencies.AndroidX
import com.itdroid.pocketkotlin.Dependencies.DebugTools
import com.itdroid.pocketkotlin.Dependencies.Google
import com.itdroid.pocketkotlin.Dependencies.GooglePlay
import com.itdroid.pocketkotlin.Dependencies.KotlinX
import com.itdroid.pocketkotlin.Dependencies.Module
import com.itdroid.pocketkotlin.Dependencies.Tests
import com.itdroid.pocketkotlin.Dependencies.jarLibs
import com.itdroid.pocketkotlin.Dependencies.kotlinStdLib

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    kotlin("kapt")
    id("com.google.android.gms.oss-licenses-plugin")
}

android {

    compileSdkVersion(AppComponents.AndroidSdk.compileSdk)
    buildToolsVersion(AppComponents.AndroidSdk.buildTools)

    defaultConfig {
        applicationId = "com.itdroid.pocketkotlin"

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
        useIR = true
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerVersion = Dependencies.kotlinVersion
        kotlinCompilerExtensionVersion = AndroidX.Compose.version
    }
}

dependencies {
    implementation(jarLibs)
    implementation(kotlinStdLib)

    implementation(project(Module.projects))
    implementation(project(Module.syntax))
    implementation(project(Module.utils))
    implementation(project(Module.preferences))

    testImplementation(Tests.junit)
    androidTestImplementation(Tests.androidxJunit)
    androidTestImplementation(Tests.espressoCore)

    debugImplementation(DebugTools.leakCanary)

    implementation(KotlinX.coroutines)

    implementation(AndroidX.appCompat)
    implementation(AndroidX.activityKtx)
    implementation(AndroidX.coreKtx)

    implementation(AndroidX.liveData)
    implementation(AndroidX.viewModelSaveState)

    implementation(Google.material)
    implementation(GooglePlay.ossLicenses)

    implementation(AndroidX.Compose.runtime)
    implementation(AndroidX.Compose.uiTooling)
    implementation(AndroidX.Compose.material)
    implementation(AndroidX.Compose.liveData)

}
