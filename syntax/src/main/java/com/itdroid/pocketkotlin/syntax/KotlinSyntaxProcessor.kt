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
        indent: String = "",
    ) {
        when (tree) {
            is TerminalNode -> maybeEmitKeyword(collector, tree)
            is RuleNode -> {
                enterBranch(indent, tree)
                checkBranch(collector, tree)
                for (i in 0 until tree.childCount) {
                    process(collector, tree.getChild(i), "$indent ")
                }
                exitBranch(tree)
            }
        }
    }

    private fun enterBranch(indent: String, tree: RuleNode) {
        iLog { "$indent enter: ${tree::class.java.simpleName}: ${tree.text}" }
        when (tree) {
            is KotlinParser.TopLevelObjectContext -> contextStack.push(Scope.TopLevel)
            is KotlinParser.ClassParametersContext -> contextStack.push(Scope.ClassParameters)
            is KotlinParser.ClassBodyContext -> contextStack.push(Scope.ClassBody)
            is KotlinParser.PropertyDeclarationContext -> {
                @Suppress("NON_EXHAUSTIVE_WHEN")
                when (contextStack.peek()) {
                    Scope.TopLevel, Scope.ClassBody -> contextStack.push(Scope.PropertyDeclaration)
                }
            }
            is KotlinParser.FunctionDeclarationContext -> contextStack.push(Scope.FunDeclaration)
            is KotlinParser.FunctionBodyContext -> contextStack.push(Scope.FunBody)
            is KotlinParser.EnumEntryContext -> contextStack.push(Scope.EnumEntry)

            is KotlinParser.FunctionValueParametersContext -> contextStack.push(Scope.Parameters)
        }
    }

    private fun exitBranch(tree: RuleNode) {
        when (tree) {
            is KotlinParser.TopLevelObjectContext,
            is KotlinParser.ClassParametersContext,
            is KotlinParser.ClassBodyContext,
            is KotlinParser.FunctionDeclarationContext,
            is KotlinParser.FunctionBodyContext,
            is KotlinParser.FunctionValueParametersContext,
            -> contextStack.pop()
            is KotlinParser.PropertyDeclarationContext -> {
                if (contextStack.peek() == Scope.PropertyDeclaration) contextStack.pop()
            }
        }
    }

    private suspend fun checkBranch(collector: FlowCollector<SyntaxToken>, tree: RuleNode) {
        when (tree) {
            is KotlinParser.SimpleIdentifierContext -> {
                @Suppress("NON_EXHAUSTIVE_WHEN")
                when (contextStack.peek()) {
                    Scope.EnumEntry,
                    Scope.ClassParameters,
                    Scope.PropertyDeclaration,
                    ->
                        collector.emit(SyntaxToken(tree.range(), PropertyMarker))
                    Scope.FunDeclaration ->
                        collector.emit(SyntaxToken(tree.range(), FunctionMarker))
                }
            }
            is KotlinParser.StringLiteralContext -> {
                collector.emit(SyntaxToken(tree.range(), StrCharLiteralMarker))
            }
        }
    }

    private suspend fun maybeEmitKeyword(
        collector: FlowCollector<SyntaxToken>,
        node: TerminalNode?,
    ) {
        val type = node?.symbol?.type ?: return
        when (type) {
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
    EnumEntry,
    FunBody,
    PropertyDeclaration,
    FunDeclaration,
    Parameters,
}

private fun ParserRuleContext.range(): IntRange =
    start.startIndex..stop.stopIndex + 1

private fun TerminalNode.range(): IntRange =
    symbol.startIndex..symbol.stopIndex + 1