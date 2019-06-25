package com.pmarchenko.itdroid.trykotlin.extentions

import android.view.View
import android.view.View.GONE

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
fun View.setVisibility(isVisible: Boolean) {
    visibility = if(isVisible) View.VISIBLE else GONE
}