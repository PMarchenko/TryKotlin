package com.itdroid.pocketkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.itdroid.pocketkotlin.database.content.DatabaseContentManager
import com.itdroid.pocketkotlin.database.entity.AppDatabaseInfo
import com.itdroid.pocketkotlin.database.entity.Example
import com.itdroid.pocketkotlin.database.entity.Project
import com.itdroid.pocketkotlin.database.entity.ProjectFile
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author itdroid
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
                        Room.databaseBuilder(
                            ctx,
                            AppDatabase::class.java,
                            AppDatabaseInfo.NAME
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(object : Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    GlobalScope.launch {
                                        DatabaseContentManager(ctx).fillDatabase(getDatabase(ctx))
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

