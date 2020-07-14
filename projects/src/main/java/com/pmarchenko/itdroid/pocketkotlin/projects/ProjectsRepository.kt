package com.pmarchenko.itdroid.pocketkotlin.projects

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.pmarchenko.itdroid.pocketkotlin.database.AppDatabase
import com.pmarchenko.itdroid.pocketkotlin.database.ProjectDao
import com.pmarchenko.itdroid.pocketkotlin.network.KotlinProjectNetworkExecutionService
import com.pmarchenko.itdroid.pocketkotlin.network.ProjectExecutionService
import com.pmarchenko.itdroid.pocketkotlin.projects.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Pavel Marchenko
 */
class ProjectsRepository private constructor(
    private val projectDao: ProjectDao,
    private val executionService: ProjectExecutionService
) {

    val userProjects: LiveData<List<Project>> = projectDao.getUserProjects()
        .map {
            it.map { dbProject -> dbProject.fromDbProject() }
        }

    val examples: LiveData<List<Example>> = projectDao.getExamples()
        .map {
            it.map { dbExample -> dbExample.fromDbExample() }
        }

    suspend fun addProject(project: Project) = projectDao.insert(project.toDbProject())

    suspend fun deleteProject(project: Project) {
        projectDao.deleteProject(project.toDbProject())
    }

    fun loadProject(projectId: Long): LiveData<Project?> = projectDao.getProject(projectId)
        .map { it?.fromDbProject() }

    suspend fun updateFile(project: Project, file: ProjectFile) {
        projectDao.updateFile(
            project.toDbProject(dateModified = System.currentTimeMillis()),
            file.toDbProjectFile(dateModified = System.currentTimeMillis())
        )
    }

    suspend fun updateProject(project: Project) {
        projectDao.updateProject(project.toDbProject(dateModified = System.currentTimeMillis()))
    }

    fun execute(project: Project): Flow<Resource<ProjectExecutionResult>> =
        flow {
            val response = try {
                executionService.execute(
                    project.executionType,
                    project.runConfig,
                    project.toApiProject(),
                    project.mainFile,
                    project.searchForMain
                )
            } catch (e: Throwable) {
                //todo proper error message
                emit(Error(e.message ?: ""))
                return@flow
            }

            if (response.isSuccessful) {
                //todo proper error message
                response.body()?.let { emit(Success(it.fromApiExecutionResult())) }
                    ?: emit(Error(""))
            } else {
                //todo proper error message
                //todo blocking call?
                emit(Error(response.errorBody()?.string() ?: ""))
            }
        }

    companion object Factory {
        fun newInstance(ctx: Context): ProjectsRepository {
            val projectDao = AppDatabase.getDatabase(ctx).getProjectDao()
            val executionService = KotlinProjectNetworkExecutionService
            return ProjectsRepository(projectDao, executionService)
        }
    }
}