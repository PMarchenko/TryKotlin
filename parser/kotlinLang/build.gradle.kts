import com.itdroid.pocketkotlin.AppComponents
import com.itdroid.pocketkotlin.Dependencies.jarLibs

plugins {
    id("java-library")
}

java {
    sourceCompatibility = AppComponents.CompileOptions.sourceCompatibility
    targetCompatibility = AppComponents.CompileOptions.targetCompatibility
}

dependencies {
    api(jarLibs)
}
