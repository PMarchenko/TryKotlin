package com.pmarchenko.itdroid.pocketkotlin.domain.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pmarchenko.itdroid.pocketkotlin.domain.db.content.DatabaseContentManager
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.Example
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.ProjectFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author Pavel Marchenko
 */
@Database(
    entities = [Project::class, ProjectFile::class, Example::class],
    version = AppDatabaseInfo.VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getProjectDao(): ProjectDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    AppDatabaseInfo.NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            GlobalScope.launch(Dispatchers.IO) {
                                DatabaseContentManager(context).fillDatabase(getDatabase(context))
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

