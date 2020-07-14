import com.pmarchenko.itdroid.pocketkotlin.Dependencies.Modules

rootProject.buildFileName = "build.gradle.kts"

include(
    Modules.app,
    Modules.projects,
    Modules.syntax,
    Modules.utils,
    Modules.database,
    Modules.network
)
