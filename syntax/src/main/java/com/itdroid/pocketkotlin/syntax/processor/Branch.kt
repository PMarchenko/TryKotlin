package com.itdroid.pocketkotlin.syntax.processor

/**
 * @author Pavel Marchenko
 */
internal sealed class Branch(val position: Int)

internal class StringLiteral(position: Int) : Branch(position)

internal class StringExpression(position: Int) : Branch(position)

