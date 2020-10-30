package com.pmarchenko.itdroid.pocketkotlin.utils

/**
 * @author Pavel Marchenko
 */

fun Int.asRange(end: Int) = this.toLong().shl(32) or (end.toLong() and 0xFFFFFFFF)
fun Long.rangeStart() = this.shr(32).toInt()
fun Long.rangeEnd() = this.and(0xFFFFFFFF).toInt()
