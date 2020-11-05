package com.itdroid.pocketkotlin.preferences

import com.itdroid.pocketkotlin.utils.applyRange

/**
 * @author itdroid
 */
object LogsPanelWeightPreference {

    const val DEFAULT = 0.7f

    const val MAX = 1f

    internal val filter: Filter<Float> = object : Filter<Float> {
        override fun apply(data: Float) = data.applyRange(0.15f, 0.85f)
    }
}