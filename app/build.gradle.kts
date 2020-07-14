import com.pmarchenko.itdroid.pocketkotlin.AppComponents
import com.pmarchenko.itdroid.pocketkotlin.AppVersion
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.DebugTools
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.KotlinX
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.AndroidX
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.Google
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.Modules.projectsModule
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.Modules.syntaxModule
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.Modules.utilsModule
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.Tests
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.kotlinStdLib
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.jarLibs

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    kotlin("kapt")
}

android {

    compileSdkVersion(AppComponents.AndroidSdk.compileSdk)
    buildToolsVersion(AppComponents.AndroidSdk.buildTools)

    defaultConfig {
        applicationId = "com.pmarchenko.itdroid.pocketkotlin"

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

    viewBinding {
        isEnabled = true
    }
}

dependencies {
    implementation(jarLibs)
    implementation(kotlinStdLib)

    implementation(projectsModule)
    implementation(syntaxModule)
    implementation(utilsModule)

    testImplementation(Tests.junit)
    androidTestImplementation(Tests.androidxJunit)
    androidTestImplementation(Tests.espressoCore)

    debugImplementation(DebugTools.leakCanary)

    implementation(KotlinX.coroutines)

    implementation(AndroidX.appCompat)
    implementation(AndroidX.fragments)
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.viewPager)

    implementation(AndroidX.navigationFragment)
    implementation(AndroidX.navigationFragmentKtx)
    implementation(AndroidX.navigationUi)
    implementation(AndroidX.navigationUiKtx)

    implementation(AndroidX.lifecycleExt)
    implementation(AndroidX.lifecycleExtKtx)

    implementation(Google.material)
}
