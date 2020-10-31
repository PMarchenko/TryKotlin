package com.itdroid.pocketkotlin.database.content

import android.content.Context
import androidx.annotation.WorkerThread
import com.google.gson.Gson
import com.itdroid.pocketkotlin.database.AppDatabase
import com.itdroid.pocketkotlin.database.ProjectDao
import com.itdroid.pocketkotlin.database.entity.AppDatabaseInfo
import com.itdroid.pocketkotlin.database.entity.ProjectType
import com.itdroid.pocketkotlin.utils.eLog
import java.io.InputStreamReader
import com.itdroid.pocketkotlin.database.entity.Example as DBExample
import com.itdroid.pocketkotlin.database.entity.Project as DBProject
import com.itdroid.pocketkotlin.database.entity.ProjectFile as DBFile


/**
 * @author itdroid
 */
@WorkerThread
internal class DatabaseContentManager(private val context: Context) {

    internal suspend fun fillDatabase(db: AppDatabase) {
        try {
            val dao = db.getProjectDao()
            fillExamples(dao)
        } catch (e: Exception) {
            eLog(e) {   "Cannot prepare database examples" }
            // try next time
            context.deleteDatabase(AppDatabaseInfo.NAME)
            throw e
        }
    }

    private suspend fun fillExamples(dao: ProjectDao) {
        for (example in readExamples()) {
            dao.insert(example.dbEntity())
        }
    }

    private fun readExamples(): List<Example> {
        val stream = context.assets.open("db/kotlin_examples.json")
        return stream.use {
            val reader = InputStreamReader(it)
            val examplesWrapper = Gson().fromJson(reader, Examples::class.java)
            examplesWrapper.examples
        }
    }
}

private data class Examples(
    var examples: List<Example>
)

private data class Example(
    val category: String,
    val description: String,
    var project: Project
)

private data class Project(
    val id: String,
    val name: String,
    val searchForMain: Boolean,
    var args: String,
    val confType: String,
    val originUrl: String? = null,
    val readOnlyFileNames: List<String>,
    var files: MutableList<File>
)

private data class File(
    val publicId: String,
    val name: String,
    var text: String
)

private fun Example.dbEntity(): DBExample =
    DBExample(
        category = category,
        description = description
    ).apply {
        exampleProject = project.dbEntity()
        modifiedProject = project.dbEntity()
    }

private fun Project.dbEntity(): DBProject =
    DBProject(
        publicId = id,
        projectType = ProjectType.Example,
        name = name,
        mainFile = files.first().name,
        searchForMain = searchForMain,
        dateCreated = System.currentTimeMillis(),
        dateModified = System.currentTimeMillis(),
        args = args,
        runConfig = confType,
        originUrl = originUrl,
        readOnlyFileNames = readOnlyFileNames
    ).apply {
        files = this@dbEntity.files.mapTo(mutableListOf(), File::dbEntity)
    }

private fun File.dbEntity(): DBFile =
    DBFile(
        publicId = publicId,
        name = name,
        dateCreated = System.currentTimeMillis(),
        dateModified = System.currentTimeMillis(),
        program = text
    )
