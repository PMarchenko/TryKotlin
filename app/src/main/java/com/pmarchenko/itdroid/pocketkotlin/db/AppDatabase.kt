package com.pmarchenko.itdroid.pocketkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pmarchenko.itdroid.pocketkotlin.model.project.Project
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.utils.async

/**
 * @author Pavel Marchenko
 */
@Database(entities = [Project::class, ProjectFile::class], version = AppDatabaseInfo.VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getProjectDao(): ProjectDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, AppDatabaseInfo.NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            async {
                                getDatabase(context).getProjectDao().insert(Project.helloWorld("HelloWorld"))
                            }
                        }
                    })
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}

