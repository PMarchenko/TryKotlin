package com.itdroid.pocketkotlin.database.entity

/**
 * @author itdroid
 */
import android.text.TextUtils
import androidx.room.TypeConverter

internal object ProjectConverter {

    @TypeConverter
    @JvmStatic
    fun convertProjectType(type: ProjectType) = type.name

    @TypeConverter
    @JvmStatic
    fun convertProjectType(type: String) = ProjectType.valueOf(type)

    @TypeConverter
    @JvmStatic
    fun convertProjectStatus(type: ProjectStatus) = type.name

    @TypeConverter
    @JvmStatic
    fun convertProjectStatus(type: String) = ProjectStatus.valueOf(type)

    @TypeConverter
    @JvmStatic
    fun convertReadOnlyFileNames(names: List<String>): String = TextUtils.join(",", names)

    @TypeConverter
    @JvmStatic
    fun convertReadOnlyFileNames(names: String): List<String> = names.split(",")

}
