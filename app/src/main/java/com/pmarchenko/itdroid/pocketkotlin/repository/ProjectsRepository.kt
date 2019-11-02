package com.pmarchenko.itdroid.pocketkotlin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pmarchenko.itdroid.pocketkotlin.db.ProjectDao
import com.pmarchenko.itdroid.pocketkotlin.db.entity.*
import com.pmarchenko.itdroid.pocketkotlin.model.Error
import com.pmarchenko.itdroid.pocketkotlin.model.Resource
import com.pmarchenko.itdroid.pocketkotlin.model.Success
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectExecutionResult
import com.pmarchenko.itdroid.pocketkotlin.network.ProjectExecutionService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Pavel Marchenko
 */
class ProjectsRepository(
    private val projectDao: ProjectDao,
    private val executionService: ProjectExecutionService
) {

    val userProjects: LiveData<List<Project>> = Transformations.map(projectDao.getUserProjects()) { projectsWithFiles ->
        projectsWithFiles.map { projectWithFiles: ProjectWithFiles ->
            toProject(projectWithFiles)
        }
    }

    val examples: LiveData<List<Example>> = Transformations.map(projectDao.getExamples()) { projectsWithFiles ->
        projectsWithFiles.map { exampleWithProject: ExampleWithProjects ->
            val example = exampleWithProject.example
            example.exampleProject = toProject(exampleWithProject.exampleProjectWithFiles)
            example.modifiedProject = toProject(exampleWithProject.modifiedProjectWithFiles)
            example
        }
    }

    fun addProject(project: Project) = projectDao.insert(project)

    fun deleteProject(project: Project) {
        projectDao.deleteProject(project)
    }

    fun loadProject(projectId: Long) = Transformations.map(projectDao.getProject(projectId)) { toProject(it) }

    fun updateFile(project: Project, file: ProjectFile, updateTimestamp: Boolean = true) {
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

    fun updateProject(project: Project, updateTimestamp: Boolean = true) {
        projectDao.updateProject(
            if (updateTimestamp) {
                project.copy(dateModified = System.currentTimeMillis())
            } else {
                project
            }
        )
    }

    fun resetExampleProject(project: Project) {
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
    }
}