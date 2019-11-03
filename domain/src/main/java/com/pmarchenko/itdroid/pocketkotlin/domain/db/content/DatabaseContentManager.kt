package com.pmarchenko.itdroid.pocketkotlin.domain.db.content

import android.content.Context
import androidx.annotation.WorkerThread
import com.google.gson.GsonBuilder
import com.pmarchenko.itdroid.pocketkotlin.domain.BuildConfig
import com.pmarchenko.itdroid.pocketkotlin.domain.db.AppDatabase
import com.pmarchenko.itdroid.pocketkotlin.domain.db.ProjectDao
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.Example
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.ProjectType
import com.pmarchenko.itdroid.pocketkotlin.data.model.project.Language
import java.io.FileNotFoundException
import java.io.InputStreamReader

/**
 * @author Pavel Marchenko
 */
@WorkerThread
class DatabaseContentManager(private val context: Context) {

    fun fillDatabase(db: AppDatabase) {
        val dao = db.getProjectDao()
        fillHelloWorldProject(dao)
        fillExamples(dao)
    }

    private fun fillHelloWorldProject(dao: ProjectDao) {
        val project = Project.empty("Hello World")
        project.files[0].program = """
                /**
                 * Hello, World!
                 */
                fun main(args: Array<String>) {
                    println("Hello, World!")
                }
            """.trimIndent()

        dao.insert(project)
    }

    private fun fillExamples(dao: ProjectDao) {
        val examples = readExamples()

        val timestamp = System.currentTimeMillis()
        for (example in examples) {
            example.exampleProject?.let {
                val project = it.copy(
                    dateCreated = timestamp,
                    executionType = "run",
                    language = Language.KOTLIN,
                    mainFile = it.files[0].name,
                    projectType = ProjectType.EXAMPLE
                )

                for (file in project.files) {
                    file.dateCreated = timestamp
                }

                example.exampleProject = project
                example.modifiedProject = project.copy()
            }
        }
        dao.insertExamples(examples)
    }

    private fun readExamples(): List<Example> {
        try {
            val stream = context.assets.open("db/kotlin_examples.json1")
            return stream.use {
                val reader = InputStreamReader(it)
                val examplesWrapper = GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create()
                    .fromJson(reader, Examples::class.java)
                examplesWrapper.examples
            }
        } catch (e: FileNotFoundException) {
            if (BuildConfig.DEBUG) {
                return emptyList()
            } else {
                throw e
            }
        }
    }
}