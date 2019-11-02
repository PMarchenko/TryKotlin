package com.pmarchenko.itdroid.pocketkotlin.db.content

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.pmarchenko.itdroid.pocketkotlin.db.entity.Example

/**
 * @author Pavel Marchenko
 */
data class Examples(

    @Expose
    @SerializedName("examples")
    var examples: List<Example>
)