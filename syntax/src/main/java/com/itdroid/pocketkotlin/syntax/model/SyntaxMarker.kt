package com.itdroid.pocketkotlin.syntax.model

/**
 * @author itdroid
 */

internal sealed class SyntaxMarker

internal object DocCommentMarker : SyntaxMarker()

internal object CommentMarker : SyntaxMarker()

internal object KeywordMarker : SyntaxMarker()

internal object PropertyMarker : SyntaxMarker()

internal object FunctionMarker : SyntaxMarker()

internal object StrCharLiteralMarker : SyntaxMarker()

internal object NumberMarker : SyntaxMarker()
