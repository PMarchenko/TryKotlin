package com.itdroid.pocketkotlin.utils

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource

/**
 * @author itdroid
 */
data class ImageInput(

    private val vector: VectorAsset? = null,

    @DrawableRes private val vectorResId: Int = -1,

    private val image: ImageAsset? = null,

    @DrawableRes private val imageResId: Int = -1,

    val colorFilter: ColorFilter? = null,
) {
    @Composable
    fun image(): ImageAsset = image ?: imageResource(imageResId)

    @Composable
    fun vector(): VectorAsset = vector ?: vectorResource(vectorResId)

    fun hasVector() = vector != null || vectorResId != -1

    fun hasImage() = image != null || imageResId != -1
}

fun ImageInput.tint(color: Color?): ImageInput =
    color?.let { copy(colorFilter = ColorFilter.tint(it)) }
        ?: this
