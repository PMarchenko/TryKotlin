package com.itdroid.pocketkotlin.syntax.model

import com.itdroid.pocketkotlin.utils.asRange

/**
 * @author itdroid
 */
internal class SyntaxInfo {

    private val _keywords = mutableListOf<Long>()
    val keywordRanges: List<Long> = _keywords
    fun addKeyWord(start: Int, end: Int) {
        _keywords.addRange(start, end)
    }

    private val _propertyNames = mutableListOf<Long>()
    val propertyNames: List<Long> = _propertyNames
    fun addPropertyName(start: Int, end: Int) {
        _propertyNames.addRange(start, end)
    }

    private val _funNames = mutableListOf<Long>()
    val funNames: List<Long> = _funNames
    fun addFunName(start: Int, end: Int) {
        _funNames.addRange(start, end)
    }

    private val _strCharLiterals = mutableListOf<Long>()
    val strCharLiterals: List<Long> = _strCharLiterals
    fun addStrCharLiteral(start: Int, end: Int) {
        _strCharLiterals.addRange(start, end)
    }

    private val _numberLiterals = mutableListOf<Long>()
    val numberLiterals: List<Long> = _numberLiterals
    fun addNumberLiteral(start: Int, end: Int) {
        _numberLiterals.addRange(start, end)
    }

    private fun MutableList<Long>.addRange(start: Int, end: Int) {
        add(start.asRange(end))
    }

    val empty: Boolean
        get() = _keywords.isEmpty() &&
                _propertyNames.isEmpty() &&
                _funNames.isEmpty() &&
                _strCharLiterals.isEmpty() &&
                _numberLiterals.isEmpty()

    val notEmpty: Boolean
        get() = !empty

}