import com.pmarchenko.itdroid.pocketkotlin.AppComponents
import com.pmarchenko.itdroid.pocketkotlin.Dependencies.jarLibs

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
