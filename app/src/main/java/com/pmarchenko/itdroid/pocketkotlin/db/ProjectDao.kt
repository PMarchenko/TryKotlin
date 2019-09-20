package com.pmarchenko.itdroid.pocketkotlin.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pmarchenko.itdroid.pocketkotlin.db.entity.ProjectType
import com.pmarchenko.itdroid.pocketkotlin.db.entity.ProjectWithFiles
import com.pmarchenko.itdroid.pocketkotlin.model.project.Project
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectFile

/**
 * @author Pavel Marchenko
 */
@Dao
abstract class ProjectDao {

    @Query(
        """
            SELECT *
            FROM ${ProjectTable.TABLE}
            WHERE ${ProjectTable.PROJECT_TYPE} = '${ProjectType.DB_PROJECT}'
            ORDER BY ${ProjectTable.DATE_MODIFIED} DESC
              """
    )
    @Transaction
    abstract fun getMyProjects(): LiveData<List<ProjectWithFiles>>

    @Query(
        """
            SELECT * 
            FROM ${ProjectTable.TABLE}
            WHERE ${ProjectTable.PROJECT_TYPE} = '${ProjectType.DB_PROJECT}' AND ${ProjectTable.ID} = :projectId
            ORDER BY ${ProjectTable.DATE_MODIFIED} DESC
              """
    )
    @Transaction
    abstract fun getProject(projectId: Long): LiveData<ProjectWithFiles>

    @Transaction
    open fun insert(project: Project): Long {
        val projectId = insertPrivate(project)
        project.files.forEach { file ->
            file.projectId = projectId
            insertPrivate(file)
        }
        return projectId
    }

    @Insert
    abstract fun insertPrivate(project: Project): Long

    @Insert
    abstract fun insertPrivate(file: ProjectFile): Long

    @Delete
    abstract fun deleteProject(project: Project)

    @Update
    abstract fun updateFile(project: Project, file: ProjectFile)

    @Update
    abstract fun updateProject(project: Project)

}