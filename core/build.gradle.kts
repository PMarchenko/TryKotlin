import com.pmarchenko.itdroid.pocketkotlin.Versions
import com.pmarchenko.itdroid.pocketkotlin.kotlinStdLib

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {

    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildTools)

    defaultConfig {
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)

        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to "*.jar")))

    testImplementation("junit:junit:${Versions.junit}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.androidxJunit}")

    implementation(kotlinStdLib)

    implementation("androidx.recyclerview:recyclerview:1.1.0-rc01")
    implementation("androidx.fragment:fragment:1.2.0-rc01")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0-rc01")
}
