package com.itdroid.pocketkotlin.syntax.processor

import com.itdroid.pocketkotlin.syntax.model.*
import com.itdroid.pocketkotlin.syntax.parser.kotlin.KotlinParser
import com.itdroid.pocketkotlin.utils.iLog
import kotlinx.coroutines.flow.FlowCollector
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode
import java.util.*

/**
 * @author Pavel Marchenko
 */
internal class KotlinSyntaxProcessor {

    private val branches = LinkedList<Branch>()

    suspend fun process(
        collector: FlowCollector<SyntaxToken>,
        tree: ParseTree,
    ) {
        when (tree) {
            is TerminalNode -> maybeEmitKeyword(collector, tree)
            is RuleNode -> {
                for (i in 0 until tree.childCount) {
                    process(collector, tree.getChild(i))
                }
            }
        }
    }

    private suspend fun maybeEmitKeyword(
        collector: FlowCollector<SyntaxToken>,
        node: TerminalNode,
    ) {
        val type = node.symbol?.type ?: return

        val start = node.start
        val end = node.end
        val range = node.range

        iLog { "terminal: $type, -> ${node.text}" }
        when (type) {
            //Comments
            KotlinParser.LineComment -> collector.emit(SyntaxToken(range, CommentMarker))
            KotlinParser.DelimitedComment -> {
                val comment = node.symbol?.text
                if (comment?.startsWith("/**") == true) {
                    collector.emit(SyntaxToken(range, DocCommentMarker))
                } else {
                    collector.emit(SyntaxToken(range, CommentMarker))
                }
            }

            //Keywords
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
                collector.emit(SyntaxToken(range, KeywordMarker))

            //Strings
            KotlinParser.QUOTE_OPEN, KotlinParser.TRIPLE_QUOTE_OPEN ->
                branches.push(StringLiteral(start))
            KotlinParser.QUOTE_CLOSE, KotlinParser.TRIPLE_QUOTE_CLOSE ->
                if (branches.peek() is StringLiteral) {
                    val strStart = branches.pop().position
                    collector.emit(SyntaxToken(strStart..end, StrCharLiteralMarker))
                } else error("Expected 'StringLiteral' branch, has '${branches.peek()}'")

            KotlinParser.UnsignedLiteral,
            KotlinParser.LongLiteral,
            KotlinParser.IntegerLiteral, KotlinParser.HexLiteral, KotlinParser.BinLiteral,
            KotlinParser.RealLiteral, KotlinParser.FloatLiteral, KotlinParser.DoubleLiteral,
            ->
                collector.emit(SyntaxToken(range, NumberMarker))

            KotlinParser.CharacterLiteral,
            ->
                collector.emit(SyntaxToken(range, StrCharLiteralMarker))

        }
    }
}

private val TerminalNode.start: Int
    get() = symbol.startIndex

private val TerminalNode.end: Int
    get() = symbol.stopIndex + 1

private val TerminalNode.range: IntRange
    get() = start..end