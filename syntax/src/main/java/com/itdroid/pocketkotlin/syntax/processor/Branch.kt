package com.itdroid.pocketkotlin.syntax.processor

/**
 * @author Pavel Marchenko
 */
sealed class Branch(val position: Int)

class StringLiteral(position: Int) : Branch(position)

