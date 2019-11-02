package com.pmarchenko.itdroid.pocketkotlin.db

/**
 * @author Pavel Marchenko
 */
import android.text.TextUtils
import androidx.room.TypeConverter
import com.pmarchenko.itdroid.pocketkotlin.db.entity.ProjectType
import com.pmarchenko.itdroid.pocketkotlin.model.project.Language

object ProjectConverter {

    @TypeConverter
    @JvmStatic
    fun convertProjectLanguage(language: Language) = language.name

    @TypeConverter
    @JvmStatic
    fun convertProjectLanguage(language: String) = Language.valueOf(language)

    @TypeConverter
    @JvmStatic
    fun convertProjectType(type: ProjectType) = type.name

    @TypeConverter
    @JvmStatic
    fun convertProjectType(type: String) = ProjectType.valueOf(type)

    @TypeConverter
    @JvmStatic
    fun convertReadOnlyFileNames(fileNames: List<String>): String = TextUtils.join(",", fileNames)

    @TypeConverter
    @JvmStatic
    fun convertReadOnlyFileNames(fileNames: String): List<String> = fileNames.split(",")

}