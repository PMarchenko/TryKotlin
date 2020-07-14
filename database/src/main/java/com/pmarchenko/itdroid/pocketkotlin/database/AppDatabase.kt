package com.pmarchenko.itdroid.pocketkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pmarchenko.itdroid.pocketkotlin.database.content.DatabaseContentManager
import com.pmarchenko.itdroid.pocketkotlin.database.entity.AppDatabaseInfo
import com.pmarchenko.itdroid.pocketkotlin.database.entity.Example
import com.pmarchenko.itdroid.pocketkotlin.database.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.database.entity.ProjectFile
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
            return INSTANCE
                ?: synchronized(this) {
                val ctx = context.applicationContext
                val instance =
                    Room.databaseBuilder(ctx, AppDatabase::class.java,
                        AppDatabaseInfo.NAME
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                //todo remove global scope
                                GlobalScope.launch {
                                    DatabaseContentManager(ctx).fillDatabase(
                                        getDatabase(
                                            ctx
                                        )
                                    )
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

