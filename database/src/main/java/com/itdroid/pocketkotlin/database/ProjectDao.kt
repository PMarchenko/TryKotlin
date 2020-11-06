package com.itdroid.pocketkotlin.database

import androidx.room.*
import com.itdroid.pocketkotlin.database.entity.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author itdroid
 */
@Dao
abstract class ProjectDao {

    val userProjects: Flow<List<Project>>
        get() = getUserProjects().map { projects ->
            projects.map(ProjectWithFiles::toProject)
        }

    val softDeletedProjects: Flow<List<Project>>
        get() = getSoftDeletedProjects().map { projects ->
            projects.map(ProjectWithFiles::toProject)
        }

    val examples: Flow<List<Example>>
        get() = getExamples().map { examples ->
            examples.map(ExampleWithProjects::toExample)
        }

    fun projectFlow(projectId: Long): Flow<Project?> = projectFlowInternal(projectId)
        .map { it?.toProject() }

    @Query(
        """
            SELECT * 
            FROM ${ProjectsTable.TABLE}
            WHERE ${ProjectsTable.ID} = :projectId
            LIMIT 1
              """
    )
    internal abstract fun projectFlowInternal(projectId: Long): Flow<ProjectWithFiles?>

    @Query(
        """
            SELECT *
            FROM ${ProjectsTable.TABLE}
            WHERE ${ProjectsTable.PROJECT_TYPE} IN ('${ProjectType.DB_USER_PROJECT}', '${ProjectType.DB_MODIFIED_EXAMPLE}')
                    AND ${ProjectsTable.STATUS} != '${ProjectStatus.DB_SOFT_DELETED}'
            ORDER BY ${ProjectsTable.DATE_MODIFIED} DESC
              """
    )
    internal abstract fun getUserProjects(): Flow<List<ProjectWithFiles>>

    @Query(
        """
            SELECT *
            FROM ${ProjectsTable.TABLE}
            WHERE ${ProjectsTable.STATUS} == '${ProjectStatus.DB_SOFT_DELETED}'
            ORDER BY ${ProjectsTable.DATE_SOFT_DELETED} DESC
              """
    )
    internal abstract fun getSoftDeletedProjects(): Flow<List<ProjectWithFiles>>

    @Query(
        """
            SELECT *
            FROM ${ExamplesTable.TABLE}
            ORDER BY ${ExamplesTable.CATEGORY_SORT_ORDER} ASC, ${ExamplesTable.SORT_ORDER} ASC 
              """
    )
    internal abstract fun getExamples(): Flow<List<ExampleWithProjects>>

    fun getExampleByModifiedProjectId(projectId: Long) =
        getExampleByModifiedProjectIdInternal(projectId).toExample()

    @Query(
        """
            SELECT *
            FROM ${ExamplesTable.TABLE}
            WHERE ${ExamplesTable.MODIFIED_PROJECT_ID} = :projectId
              """
    )
    internal abstract fun getExampleByModifiedProjectIdInternal(projectId: Long): ExampleWithProjects

    @Transaction
    open suspend fun insert(project: Project): Long {
        val projectId = insertInternal(project)
        for (file in project.files) {
            file.projectId = projectId
            insert(file)
        }
        return projectId
    }

    @Insert
    abstract suspend fun insert(file: ProjectFile): Long

    @Transaction
    open suspend fun insert(example: Example) {
        example.exampleProject?.let { example.exampleProjectId = insert(it) }
        example.modifiedProject?.let { example.modifiedProjectId = insert(it) }

        insertInternal(example)
    }

    @Insert
    protected abstract suspend fun insertInternal(project: Project): Long

    @Insert
    protected abstract suspend fun insertInternal(example: Example): Long

    @Update
    abstract suspend fun update(project: Project, file: ProjectFile)

    @Update
    abstract suspend fun update(project: Project): Int

    @Update
    abstract fun update(example: Example): Int

    @Delete
    abstract suspend fun delete(project: Project): Int

    @Delete
    abstract suspend fun delete(project: ProjectFile): Int

    @Query(
        """
            DELETE 
                FROM ${ProjectsTable.TABLE}
            WHERE ${ProjectsTable.STATUS} = '${ProjectStatus.DB_SOFT_DELETED}'
              """
    )
    abstract suspend fun delete(): Int

    @Transaction
    open suspend fun inTransaction(action: suspend () -> Unit) {
        action()
    }
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