import com.pmarchenko.itdroid.pocketkotlin.kotlinStdLib
import com.pmarchenko.itdroid.pocketkotlin.Versions

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    kotlin("kapt")
}

android {

    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildTools)

    defaultConfig {
        applicationId = "com.pmarchenko.itdroid.pocketkotlin"

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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
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
    implementation(project(":domain"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to "*.jar")))
    implementation(kotlinStdLib)

    testImplementation("junit:junit:${Versions.junit}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.androidxJunit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.espressoCore}")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.0-alpha-2")

    implementation("androidx.appcompat:appcompat:1.1.0")

    implementation("androidx.fragment:fragment-ktx:1.2.0-rc01")

    implementation("androidx.constraintlayout:constraintlayout:1.1.3")

    implementation("androidx.core:core-ktx:1.1.0")

    implementation("androidx.navigation:navigation-fragment:2.1.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.1.0")
    implementation("androidx.navigation:navigation-ui:2.1.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.1.0")

    implementation("androidx.lifecycle:lifecycle-extensions:2.1.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0-rc01")

    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0-rc01")
}
