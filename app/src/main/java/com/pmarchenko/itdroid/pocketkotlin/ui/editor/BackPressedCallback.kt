package com.pmarchenko.itdroid.pocketkotlin.ui.editor

/**
 * @author Pavel Marchenko
 */
class BackButtonResolver(private val delay: Long = 2000L) {

    private var backPressedTimestamp = 0L

    fun allowBackPressed(): Boolean {
        val prevTimestamp = backPressedTimestamp
        backPressedTimestamp = System.currentTimeMillis()
        return backPressedTimestamp - prevTimestamp < delay
    }
}