package com.pmarchenko.itdroid.pocketkotlin.compose

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import com.pmarchenko.itdroid.pocketkotlin.utils.ImageInput

/**
 * @author Pavel Marchenko
 */

@Composable
fun AppImage(
    image: ImageInput,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
) {
    when {
        image.hasImage() -> {
            Image(
                asset = image.image(),
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = image.colorFilter
            )
        }
        image.hasVector() -> {
            Image(
                asset = image.vector(),
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = image.colorFilter
            )
        }
        else -> error("No image asset provided")
    }
}