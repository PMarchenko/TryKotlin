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

    private val program: Node = Program()
    private var currentNode: Node = program

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

        iLog { "terminal: $type -> ${node.text}" }

        when (type) {
            KotlinParser.LPAREN -> {
                val block = currentNode.forLParent()
                currentNode.children.add(block)
                currentNode = block
            }

            KotlinParser.RPAREN -> currentNode = currentNode.parent

            KotlinParser.LCURL -> {
                val block = currentNode.forLCurl()
                currentNode.children.add(block)
                currentNode = block
            }
            KotlinParser.RCURL -> {
                currentNode = currentNode.parent
                if (currentNode is Function || currentNode is Class) {
                    currentNode = currentNode.parent
                }
                listener.maybeStringLiteralExpressionEnd(start)
            }

            //Classes
            KotlinParser.CLASS, KotlinParser.INTERFACE, KotlinParser.OBJECT -> {
                if (currentNode !is ClassBody || currentNode !is FunctionBody) {
                    currentNode = program
                }
                val classNode = Class(currentNode)
                currentNode.children.add(classNode)
                currentNode = classNode

                listener.onKeyword(start..end)
            }

            //VAL VAR
            KotlinParser.VAL, KotlinParser.VAR -> {
                val property = Property(currentNode)
                currentNode.children.add(property)

                listener.onKeyword(start..end)
            }

            // Function
            KotlinParser.FUN -> {
                if (currentNode !is ClassBody || currentNode !is Program) {
                    currentNode = program
                }
                val function = Function(currentNode)
                currentNode.children.add(function)
                currentNode = function

                listener.onKeyword(start..end)
            }

            KotlinParser.Identifier -> {
                currentNode.let {
                    if (it is Function && it.children.isEmpty()) {
                        listener.onFunctionName(start..end)
                    }
                }
            }

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
            KotlinParser.ENUM, KotlinParser.DATA, KotlinParser.INNER,
            KotlinParser.CONSTRUCTOR,
            KotlinParser.PUBLIC, KotlinParser.PROTECTED, KotlinParser.INTERNAL, KotlinParser.PRIVATE,
            KotlinParser.SUSPEND, KotlinParser.OVERRIDE,
            KotlinParser.VARARG,
            KotlinParser.INLINE, KotlinParser.NOINLINE, KotlinParser.CROSSINLINE,
            KotlinParser.OPERATOR, KotlinParser.RETURN,
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
                listener.maybeStringLiteralExpressionEnd(end)
            }
            KotlinParser.LineStrExprStart, KotlinParser.MultiLineStrExprStart -> {
                listener.onStringLiteralExpressionStart(start)
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

    fun logResult() {
        iLog { "Result: $program" }
    }
}

private val TerminalNode.start: Int
    get() = symbol.startIndex

private val TerminalNode.end: Int
    get() = symbol.stopIndex + 1
