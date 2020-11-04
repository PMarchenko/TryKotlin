package com.itdroid.pocketkotlin.syntax.processor

import com.itdroid.pocketkotlin.syntax.parser.kotlin.KotlinParser
import com.itdroid.pocketkotlin.utils.iLog
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode

/**
 * @author Pavel Marchenko
 */
internal class KotlinSyntaxProcessor(
    private val listener: KotlinSyntaxListener,
) {

    suspend fun process(tree: ParseTree) {
        when (tree) {
            is TerminalNode -> processTerminalNode(tree)
            is RuleNode -> {
                for (i in 0 until tree.childCount) {
                    process(tree.getChild(i))
                }
            }
        }
    }

    private suspend fun processTerminalNode(node: TerminalNode) {
        val type = node.symbol?.type ?: return
        val start = node.start
        val end = node.end

        iLog { "terminal: $type, -> ${node.text}" }

        when (type) {
            // Comments
            KotlinParser.LineComment -> listener.onComment(start..end)
            KotlinParser.DelimitedComment -> {
                if (node.text?.startsWith("/**") == true) {
                    listener.onDocComment(start..end)
                } else {
                    listener.onComment(start..end)
                }
            }

            // Keywords
            KotlinParser.PACKAGE,
            KotlinParser.IMPORT, KotlinParser.TYPE_ALIAS,
            KotlinParser.SEALED, KotlinParser.OPEN, KotlinParser.ABSTRACT, KotlinParser.FINAL,
            KotlinParser.CLASS, KotlinParser.INTERFACE, KotlinParser.OBJECT, KotlinParser.ENUM, KotlinParser.DATA, KotlinParser.INNER,
            KotlinParser.CONSTRUCTOR,
            KotlinParser.PUBLIC, KotlinParser.PROTECTED, KotlinParser.INTERNAL, KotlinParser.PRIVATE,
            KotlinParser.SUSPEND, KotlinParser.OVERRIDE,
            KotlinParser.VAL, KotlinParser.VAR, KotlinParser.VARARG,
            KotlinParser.FUN,
            KotlinParser.INLINE, KotlinParser.NOINLINE, KotlinParser.CROSSINLINE,
            KotlinParser.RETURN,
            KotlinParser.CONTINUE, KotlinParser.BREAK,
            KotlinParser.AS, KotlinParser.IS, KotlinParser.IN,
            KotlinParser.BY,
            KotlinParser.COMPANION,
            KotlinParser.IF, KotlinParser.ELSE, KotlinParser.FOR, KotlinParser.WHEN, KotlinParser.DO, KotlinParser.WHILE,
            KotlinParser.THROW, KotlinParser.CATCH,
            KotlinParser.CONST,
            KotlinParser.BooleanLiteral, KotlinParser.NullLiteral,
            ->
                listener.onKeyword(start..end)

            // String literals
            KotlinParser.QUOTE_OPEN, KotlinParser.TRIPLE_QUOTE_OPEN ->
                listener.onStringLiteralStart(start)

            KotlinParser.LineStrRef, KotlinParser.MultiLineStrRef -> {
                listener.onStringLiteralExpressionStart(start)
                listener.onStringLiteralExpressionEnd(end)
            }
            KotlinParser.LineStrExprStart, KotlinParser.MultiLineStrExprStart -> {
                listener.onStringLiteralExpressionStart(start)
            }
            KotlinParser.RCURL -> {
                //check if LineStrExpr end
                listener.onStringLiteralExpressionEnd(start)
            }
            KotlinParser.QUOTE_CLOSE, KotlinParser.TRIPLE_QUOTE_CLOSE ->
                listener.onStringLiteralEnd(end)

            // Char literals
            KotlinParser.CharacterLiteral,
            ->
                listener.onCharLiteral(start..end)

            // Number literals
            KotlinParser.UnsignedLiteral,
            KotlinParser.LongLiteral,
            KotlinParser.IntegerLiteral, KotlinParser.HexLiteral, KotlinParser.BinLiteral,
            KotlinParser.RealLiteral, KotlinParser.FloatLiteral, KotlinParser.DoubleLiteral,
            ->
                listener.onNumberLiteral(start..end)
        }
    }
}

private val TerminalNode.start: Int
    get() = symbol.startIndex

private val TerminalNode.end: Int
    get() = symbol.stopIndex + 1
