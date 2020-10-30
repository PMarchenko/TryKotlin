package com.pmarchenko.itdroid.pocketkotlin.projects

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.pmarchenko.itdroid.pocketkotlin.database.AppDatabase
import com.pmarchenko.itdroid.pocketkotlin.database.ProjectDao
import com.pmarchenko.itdroid.pocketkotlin.network.KotlinProjectNetworkExecutionService
import com.pmarchenko.itdroid.pocketkotlin.network.ProjectExecutionService
import com.pmarchenko.itdroid.pocketkotlin.projects.model.*
import com.pmarchenko.itdroid.pocketkotlin.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.net.HttpURLConnection

/**
 * @author Pavel Marchenko
 */
class ProjectsRepository private constructor(
    private val projectDao: ProjectDao,
    private val executionService: ProjectExecutionService,
) {

    val userProjects: LiveData<List<Project>> = projectDao.userProjects
        .map { it.map(DbProject::fromDbProject) }
        .asLiveData()

    val examples: LiveData<List<Example>> = projectDao.examples
        .map { it.map(DbExample::fromDbExample) }
        .asLiveData()

    val projectsInTrash: LiveData<List<Project>> = projectDao.softDeletedProjects
        .map { it.map(DbProject::fromDbProject) }
        .asLiveData()

    suspend fun addProject(name: String, withMain: Boolean): Long {
        val project = newProject(name, withMain)
        return projectDao.insert(project.toDbProject())
    }

    suspend fun addFile(project: Project, name: String, type: FileType): Long {
        val file = newFile(project.id, name, type)
        return projectDao.insert(file.toDbProjectFile())
    }

    suspend fun delete(project: Project): Int =
        if (project.type == ProjectType.ModifiedExample) {
            // instead of deleting modified example project
            // restore it to default state
            // and mark as soft deleted
            restoreExample(project)
            1 // one project restores
        } else {
            projectDao.update(
                project.toDbProject(
                    projectStatus = DbProjectStatus.SoftDeleted,
                    dateSoftDeleted = System.currentTimeMillis()
                )
            )
        }

    suspend fun deleteForever(project: Project) =
        projectDao.delete(project.toDbProject())

    suspend fun deleteForeverAll() =
        projectDao.delete()

    suspend fun delete(file: ProjectFile) =
        projectDao.delete(file.toDbProjectFile())

    suspend fun update(project: Project, file: ProjectFile) {
        projectDao.update(
            project.toDbProject(dateModified = System.currentTimeMillis()),
            file.toDbProjectFile(dateModified = System.currentTimeMillis())
        )
    }

    suspend fun update(project: Project) {
        projectDao.update(project.toDbProject(dateModified = System.currentTimeMillis()))
    }

    fun projectFlow(projectId: Long): Flow<Project?> =
        projectDao.projectFlow(projectId)
            .map { it?.fromDbProject() }
            .distinctUntilChanged()

    @Suppress("EXPERIMENTAL_API_USAGE")
    fun execute(project: Project): Flow<Resource> =
        flow { executeProject(project) }
            .catch { e ->
                eLog(e) { "While executing: ${project.toSimpleString()}" }
                val (message, code) = when (e) {
                    is MissingBodyExecutionResultException ->
                        "Cannot execute project" to HttpURLConnection.HTTP_INTERNAL_ERROR
                    is ExecutionException -> e.message!! to e.code
                    else -> "Cannot execute project" to HttpURLConnection.HTTP_INTERNAL_ERROR
                }
                emit(FailureResult(message, code))
            }
            .flowOn(Dispatchers.IO)

    private suspend fun FlowCollector<Resource>.executeProject(project: Project) {
        emit(Loading)

        val response = executionService.execute(
            project.executionType,
            project.runConfig,
            project.toApiProject(),
            project.mainFile,
            project.searchForMain
        )

        if (response.isSuccessful) {
            response.body()
                ?.let { emit(SuccessResult(it.fromApiExecutionResult())) }
                ?: throw MissingBodyExecutionResultException()
        } else {
            throw ExecutionException(response.code(), response.errorString() ?: "No error message")
        }
    }

    private suspend fun restoreExample(project: Project) = projectDao.inTransaction {
        val example = projectDao.getExampleByModifiedProjectId(project.id)
        example.modifiedProject?.let { modifiedProject ->
            modifiedProject.copy(
                id = 0L,
                projectType = DbProjectType.UserProject,
                projectStatus = DbProjectStatus.SoftDeleted,
                dateSoftDeleted = System.currentTimeMillis()
            ).apply {
                modifiedProject.files.mapTo(files) { file -> file.copy(id = 0L) }
            }.also { copy ->
                projectDao.insert(copy)
            }

            projectDao.delete(modifiedProject)
        }

        example.exampleProject?.let { exampleProject ->
            exampleProject.copy(id = 0)
                .apply {
                    exampleProject.files.mapTo(files) { file -> file.copy(id = 0L) }
                }.also { copy ->
                    example.modifiedProjectId = projectDao.insert(copy)
                    projectDao.update(example)
                }
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: ProjectsRepository? = null

        fun instance(ctx: Context): ProjectsRepository {
            return INSTANCE?: synchronized(this) {
                val projectDao = AppDatabase.getDatabase(ctx).getProjectDao()
                val executionService = KotlinProjectNetworkExecutionService
                val repo = ProjectsRepository(projectDao, executionService)
                INSTANCE = repo
                repo
            }
        }
    }
}
