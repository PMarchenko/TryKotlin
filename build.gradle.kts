// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0-alpha15")
        classpath(kotlin("gradle-plugin", version = "1.4.10"))
        classpath("com.google.android.gms:oss-licenses-plugin:0.10.2")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}