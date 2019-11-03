import com.pmarchenko.itdroid.pocketkotlin.Versions
import com.pmarchenko.itdroid.pocketkotlin.kotlinStdLib

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    kotlin("kapt")
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

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
        getByName("debug")
    }

    sourceSets {
        getByName("release").let {sourceSet ->
            sourceSet.java.srcDirs("src/release/java")
        }

        getByName("debug").let {sourceSet ->
            sourceSet.java.srcDirs("src/debug/java")
        }
    }
}

androidExtensions {
    isExperimental = true
}

dependencies {
    api(project(":core"))
    api(project(":data"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to "*.jar")))

    testImplementation("junit:junit:${Versions.junit}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.androidxJunit}")

    implementation(kotlinStdLib)

    debugImplementation( "com.facebook.stetho:stetho:1.5.1")
    debugImplementation( "com.facebook.stetho:stetho-okhttp3:1.5.1")

    implementation( "androidx.appcompat:appcompat:1.1.0")
    implementation( "androidx.core:core-ktx:1.1.0")
    implementation( "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.0")

    implementation( "com.squareup.retrofit2:retrofit:2.6.0")
    implementation( "com.squareup.retrofit2:converter-gson:2.6.0")

    api("androidx.room:room-runtime:2.2.1")
    implementation( "androidx.room:room-ktx:2.2.1")
    kapt ("androidx.room:room-compiler:2.2.1")
}