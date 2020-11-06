package com.itdroid.pocketkotlin.database.entity

/**
 * @author itdroid
 */
import androidx.room.TypeConverter

internal object FileConverter {

    @TypeConverter
    @JvmStatic
    fun convertProjectFileType(type: FileType) = type.name

    @TypeConverter
    @JvmStatic
    fun convertProjectFileType(type: String) = FileType.valueOf(type)

}
