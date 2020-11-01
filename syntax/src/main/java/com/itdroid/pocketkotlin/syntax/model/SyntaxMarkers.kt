package com.itdroid.pocketkotlin.syntax.model

/**
 * @author itdroid
 */
internal class SyntaxMarkers {

    private val _markers: MutableMap<IntRange, SyntaxMarker> = mutableMapOf()
    val markers: Map<IntRange, SyntaxMarker> = _markers

    fun add(range: IntRange, marker: SyntaxMarker) {
        _markers[range] = marker
    }
}
