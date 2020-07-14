package com.pmarchenko.itdroid.pocketkotlin.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.*
import com.pmarchenko.itdroid.pocketkotlin.database.entity.*

/**
 * @author Pavel Marchenko
 */
@Dao
abstract class ProjectDao {

    fun getUserProjects(): LiveData<List<Project>> = getUserProjectsInternal()
        .map { it.map(ProjectWithFiles::toProject) }

    @Query(
        """
            SELECT *
            FROM ${ProjectsTable.TABLE}
            WHERE ${ProjectsTable.PROJECT_TYPE} = '${ProjectType.DB_USER_PROJECT}'
            ORDER BY ${ProjectsTable.DATE_MODIFIED} DESC
              """
    )
    @Transaction
    internal abstract fun getUserProjectsInternal(): LiveData<List<ProjectWithFiles>>

    fun getProject(projectId: Long): LiveData<Project?> = getProjectInternal(projectId)
        .map { it?.toProject() }

    @Query(
        """
            SELECT * 
            FROM ${ProjectsTable.TABLE}
            WHERE ${ProjectsTable.ID} = :projectId
            LIMIT 1
              """
    )
    @Transaction
    internal abstract fun getProjectInternal(projectId: Long): LiveData<ProjectWithFiles?>

    @Transaction
    open suspend fun insert(project: Project): Long {
        val projectId = insertInternal(project)
        for (file in project.files) {
            file.projectId = projectId
            insertInternal(file)
        }
        return projectId
    }

    fun getExamples(): LiveData<List<Example>> = getExamplesInternal().map {
        it.map(ExampleWithProjects::toExample)
    }

    @Query(
        """
            SELECT *
            FROM ${ExamplesTable.TABLE}
              """
    )
    //TODO BUG what if wrong sort order?
    @Transaction
    internal abstract fun getExamplesInternal(): LiveData<List<ExampleWithProjects>>

    @Transaction
    open suspend fun insert(examples: List<Example>) {
        for (example in examples) {
            example.exampleProject?.let { example.exampleProjectId = insert(it) }
            example.modifiedProject?.let { example.modifiedProjectId = insert(it) }

            insertInternal(example)
        }
    }

    @Insert
    protected abstract suspend fun insertInternal(project: Project): Long

    @Insert
    protected abstract suspend fun insertInternal(file: ProjectFile): Long

    @Insert
    protected abstract fun insertInternal(example: Example): Long

    @Delete
    abstract suspend fun deleteProject(project: Project)

    @Update
    abstract suspend fun updateFile(project: Project, file: ProjectFile)

    @Update
    abstract suspend fun updateProject(project: Project)

}

private fun ProjectWithFiles.toProject(): Project {
    return project.also {
        it.files.addAll(files)
    }
}

private fun ExampleWithProjects.toExample(): Example {
    return example.apply {
        exampleProject = exampleProjectWithFiles.toProject()
        modifiedProject = modifiedProjectWithFiles.toProject()
    }
}