package com.itdroid.pocketkotlin.projects.model

/**
 * @author itdroid
 */
enum class FileType {

    File,
    Class,
    DataClass,
    Interface,
    Enum,
    Object
}

internal fun DbFileType.fromDbFileType(): FileType =
    when (this) {
        DbFileType.File -> FileType.File
        DbFileType.Class -> FileType.Class
        DbFileType.DataClass -> FileType.DataClass
        DbFileType.Interface -> FileType.Interface
        DbFileType.Enum -> FileType.Enum
        DbFileType.Object -> FileType.Object
    }

internal fun FileType.toDbFileType(): DbFileType =
    when (this) {
        FileType.File -> DbFileType.File
        FileType.Class -> DbFileType.Class
        FileType.DataClass -> DbFileType.DataClass
        FileType.Interface -> DbFileType.Interface
        FileType.Enum -> DbFileType.Enum
        FileType.Object -> DbFileType.Object
    }