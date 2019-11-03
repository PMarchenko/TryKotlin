package com.pmarchenko.itdroid.pocketkotlin.domain.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.*

/**
 * @author Pavel Marchenko
 */
@Dao
abstract class ProjectDao {

    @Query(
        """
            SELECT *
            FROM ${ProjectsTable.TABLE}
            WHERE ${ProjectsTable.PROJECT_TYPE} = '${ProjectType.DB_USER_PROJECT}'
            ORDER BY ${ProjectsTable.DATE_MODIFIED} DESC
              """
    )
    @Transaction
    abstract fun getUserProjects(): LiveData<List<ProjectWithFiles>>

    @Query(
        """
            SELECT * 
            FROM ${ProjectsTable.TABLE}
            WHERE ${ProjectsTable.ID} = :projectId
            LIMIT 1
              """
    )
    @Transaction
    abstract fun getProject(projectId: Long): LiveData<ProjectWithFiles>

    @Transaction
    open suspend fun insert(project: Project): Long {
        val projectId = insertInternal(project)
        for (file in project.files) {
            file.projectId = projectId
            insertInternal(file)
        }
        return projectId
    }

    @Query(
        """
            SELECT *
            FROM ${ExamplesTable.TABLE}
              """
    )
    //TODO BUG what if wrong sort order?
    @Transaction
    abstract fun getExamples(): LiveData<List<ExampleWithProjects>>

    @Transaction
    @Query(
        """
            SELECT *
            FROM ${ExamplesTable.TABLE}
            WHERE ${ExamplesTable.MODIFIED_PROJECT_ID} = :modifiedProjectId
              """
    )
    abstract fun getExample(modifiedProjectId: Long): ExampleWithProjects

    @Transaction
    open suspend fun insertExamples(examples: List<Example>) {
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