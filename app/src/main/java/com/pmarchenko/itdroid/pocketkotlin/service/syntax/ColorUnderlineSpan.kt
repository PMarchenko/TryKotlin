package com.pmarchenko.itdroid.pocketkotlin.service.syntax

import android.text.TextPaint
import android.text.style.UnderlineSpan
import com.pmarchenko.itdroid.pocketkotlin.extentions.dp

/**
 * @author Pavel Marchenko
 */
class ColorUnderlineSpan(val color: Int) : UnderlineSpan() {

    override fun updateDrawState(ds: TextPaint) {
        ds.isUnderlineText = false
        try {
            TextPaint::class.java.getField("underlineColor").setInt(ds, color)
            TextPaint::class.java.getField("underlineThickness").setFloat(ds, 2f.dp)
        } catch (e: Throwable) {
        }
    }
}