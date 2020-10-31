package com.itdroid.pocketkotlin.projects

import com.itdroid.pocketkotlin.projects.model.*
import com.itdroid.pocketkotlin.utils.checkAllMatched


/**
 * @author itdroid
 */
const val ProjectNameMaxLength = 100
const val FileMaxLength = 32_000
const val FileNameMaxLength = 100
const val ProjectArgsMaxLength = 1_000

internal fun newProject(name: String, includeMain: Boolean = false): Project {
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
        dateModified = timestamp,
        type = ProjectType.UserProject
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

internal fun newFile(projectId: Long, name: String, type: FileType) =
    ProjectFile(
        projectId = projectId,
        name = "$name.kt",
        publicId = name,
        dateCreated = System.currentTimeMillis(),
        dateModified = System.currentTimeMillis(),
        program = newProgram(name, type)
    )

fun newProgram(name: String, type: FileType): String =
    when (type) {
        FileType.File -> ""
        FileType.Class ->
            """
            class $name()
            """.trimIndent()
        FileType.DataClass ->
            """
            data class $name()
            """.trimIndent()
        FileType.Interface ->
            """
            interface $name {
            
            }
            """.trimIndent()
        FileType.Enum ->
            """
            enum class $name {
            
            }
            """.trimIndent()
        FileType.Object -> "object $name"
    }.checkAllMatched


val projectExamples = listOf(
    newProject("First", true),
    newProject("Second", true),
    newProject("Third", true)
)

val exampleExamples = listOf(
    Example(
        category = "Example category",
        description = "Example description",
        exampleProject = newProject("First", true),
        modifiedProject = newProject("First", true)
    ),
    Example(
        category = "Example category",
        description = "Example description",
        exampleProject = newProject("Second", true),
        modifiedProject = newProject("Second", true)
    ),
    Example(
        category = "Example category",
        description = "Example description",
        exampleProject = newProject("Third", true),
        modifiedProject = newProject("Third", true)
    )
)