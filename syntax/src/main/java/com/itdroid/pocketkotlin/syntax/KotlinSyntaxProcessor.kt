package com.itdroid.pocketkotlin.syntax

import com.itdroid.pocketkotlin.syntax.model.*
import com.itdroid.pocketkotlin.syntax.parser.kotlin.KotlinParser
import com.itdroid.pocketkotlin.utils.iLog
import kotlinx.coroutines.flow.FlowCollector
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode
import java.util.*

/**
 * @author Pavel Marchenko
 */
internal class KotlinSyntaxProcessor {

    private val contextStack = LinkedList<Scope>()

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
        iLog { "terminal: $type, -> ${node.text}" }
        when (type) {
            KotlinParser.LineComment -> collector.emit(SyntaxToken(node.range(), CommentMarker))

            KotlinParser.DelimitedComment -> {
                val comment = node.symbol?.text
                if (comment?.startsWith("/**") == true) {
                    collector.emit(SyntaxToken(node.range(), DocCommentMarker))
                } else {
                    collector.emit(SyntaxToken(node.range(), CommentMarker))
                }
            }

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
                collector.emit(SyntaxToken(node.range(), KeywordMarker))

            KotlinParser.UnsignedLiteral,
            KotlinParser.LongLiteral,
            KotlinParser.IntegerLiteral, KotlinParser.HexLiteral, KotlinParser.BinLiteral,
            KotlinParser.RealLiteral, KotlinParser.FloatLiteral, KotlinParser.DoubleLiteral,
            ->
                collector.emit(SyntaxToken(node.range(), NumberMarker))

            KotlinParser.CharacterLiteral,
            ->
                collector.emit(SyntaxToken(node.range(), StrCharLiteralMarker))

        }
    }
}

private enum class Scope {
    TopLevel,
    ClassParameters,
    ClassBody,
    FunBody,
    PropertyDeclaration,
    FunDeclaration,
    Parameters,
}

private fun ParserRuleContext.range(): IntRange =
    start.startIndex..stop.stopIndex + 1

private fun TerminalNode.range(): IntRange =
    symbol.startIndex..symbol.stopIndex + 1