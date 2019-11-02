package com.pmarchenko.itdroid.pocketkotlin.db.content

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.pmarchenko.itdroid.pocketkotlin.db.entity.Project

/**
 * @author Pavel Marchenko
 */
data class Category(
    @Expose
    @SerializedName("categoryName")
    val categoryName: String,

    @Expose
    @SerializedName("projects")
    val projects: MutableList<Project>
)