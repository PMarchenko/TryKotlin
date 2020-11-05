package com.itdroid.pocketkotlin.syntax.model

/**
 * @author itdroid
 */

internal sealed class SyntaxMarker

internal object DocCommentMarker : SyntaxMarker()

internal object CommentMarker : SyntaxMarker()

internal object KeywordMarker : SyntaxMarker()

internal object TextLiteralMarker : SyntaxMarker()

internal object NumberLiteralMarker : SyntaxMarker()

internal object PropertyNameMarker : SyntaxMarker()

internal object FunctionNameMarker : SyntaxMarker()

internal object AnnotationMarker : SyntaxMarker()

internal object AtSuffixMarker : SyntaxMarker()