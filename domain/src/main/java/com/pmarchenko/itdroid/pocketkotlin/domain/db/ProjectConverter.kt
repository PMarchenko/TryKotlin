package com.pmarchenko.itdroid.pocketkotlin.domain.db

/**
 * @author Pavel Marchenko
 */
import android.text.TextUtils
import androidx.room.TypeConverter
import com.pmarchenko.itdroid.pocketkotlin.data.model.project.Language
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.ProjectType

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
    fun convertReadOnlyFileNames(names: List<String>): String = TextUtils.join(",", names)

    @TypeConverter
    @JvmStatic
    fun convertReadOnlyFileNames(names: String): List<String> = names.split(",")

}