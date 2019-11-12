package com.pmarchenko.itdroid.pocketkotlin.domain.repository

import android.text.SpannableStringBuilder
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pmarchenko.itdroid.pocketkotlin.domain.db.ProjectDao
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.*
import com.pmarchenko.itdroid.pocketkotlin.domain.network.ProjectExecutionService
import com.pmarchenko.itdroid.pocketkotlin.data.model.Error
import com.pmarchenko.itdroid.pocketkotlin.data.model.Resource
import com.pmarchenko.itdroid.pocketkotlin.data.model.Success
import com.pmarchenko.itdroid.pocketkotlin.data.model.project.ProjectExecutionResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Pavel Marchenko
 */
class ProjectsRepository(
    private val projectDao: ProjectDao,
    private val executionService: ProjectExecutionService
) {

    val userProjects: LiveData<List<Project>> =
        Transformations.map(projectDao.getUserProjects()) { it.map(Companion::toProject) }

    val examples: LiveData<List<Example>> =
        Transformations.map(projectDao.getExamples()) { it.map(Companion::toExample) }

    suspend fun addProject(project: Project) = projectDao.insert(project)

    suspend fun deleteProject(project: Project) {
        projectDao.deleteProject(project)
    }

    fun loadProject(projectId: Long): LiveData<Project> =
        Transformations.map(projectDao.getProject(projectId), Companion::toProject)

    suspend fun updateFile(project: Project, file: ProjectFile, updateTimestamp: Boolean = true) {
        val timestamp = System.currentTimeMillis()
        projectDao.updateFile(
            if (updateTimestamp) {
                project.copy(dateModified = timestamp)
            } else {
                project
            },
            if (updateTimestamp) {
                file.copy(dateModified = timestamp)
            } else {
                file
            }
        )
    }

    suspend fun updateProject(project: Project, updateTimestamp: Boolean = true) {
        projectDao.updateProject(
            if (updateTimestamp) {
                project.copy(dateModified = System.currentTimeMillis())
            } else {
                project
            }
        )
    }

    suspend fun resetExampleProject(project: Project) {
        val projectId = project.id
        val initialFiles = projectDao.getExample(projectId).exampleProjectWithFiles.files

        for (file in project.files) {
            initialFiles
                .filter { it.name == file.name }
                .forEach { updateFile(project, file.copy(program = it.program)) }
        }
    }

    fun execute(project: Project): Flow<Resource<ProjectExecutionResult>> {
        return flow {
            try {
                val response = executionService.execute(project = project)
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result == null) {
                        //todo proper error message?
                        emit(Error(""))
                    } else {
                        emit(Success(result))
                    }
                } else {
                    emit(Error(response.errorBody()?.string() ?: ""))
                }
            } catch (e: Throwable) {
                emit(Error(e.message ?: ""))
            }
        }
    }

    companion object {

        @JvmStatic
        private fun toProject(projectWithFiles: ProjectWithFiles): Project {
            val project = projectWithFiles.project
            project.files.addAll(projectWithFiles.files)
            return project
        }

        @JvmStatic
        private fun toExample(exampleWithProject: ExampleWithProjects): Example {
            val example = exampleWithProject.example
            example.exampleProject = toProject(exampleWithProject.exampleProjectWithFiles)
            example.modifiedProject = toProject(exampleWithProject.modifiedProjectWithFiles)
            return example
        }
    }
}