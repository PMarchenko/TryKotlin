package com.pmarchenko.itdroid.pocketkotlin.projects

import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectType


/**
 * @author Pavel Marchenko
 */
fun newProject(name: String, includeMain: Boolean = false): Project {
    return if (includeMain) {
        projectWithMain(name)
    } else {
        emptyProject(name)
    }
}

internal fun emptyProject(name: String): Project {
    val timestamp = System.currentTimeMillis()
    val project = Project(
        name = name,
        mainFile = "Main.kt",
        searchForMain = true,
        dateCreated = timestamp,
        projectType = ProjectType.USER_PROJECT
    )
    project.files.add(
        ProjectFile(
            publicId = "Main",
            name = "Main.kt",
            dateCreated = timestamp
        )
    )
    return project
}

internal fun projectWithMain(name: String): Project {
    return emptyProject(name).apply {
        files[0].program = """
                /**
                 * Program entry point
                 */
                fun main(args: Array<String>) {
                    
                }
            """.trimIndent()
    }
}