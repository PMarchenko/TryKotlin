package com.pmarchenko.itdroid.pocketkotlin.preferences

import com.pmarchenko.itdroid.pocketkotlin.utils.inRange

/**
 * @author Pavel Marchenko
 */
object LogsPanelWeightPreference {

    const val DEFAULT = 0.3f

    const val MAX = 1f

    internal val filter: Filter<Float> = object : Filter<Float> {
        override fun apply(data: Float) = data.inRange(0.15f, 0.85f)
    }
}