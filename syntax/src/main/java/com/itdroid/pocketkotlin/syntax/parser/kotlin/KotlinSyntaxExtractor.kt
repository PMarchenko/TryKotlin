package com.itdroid.pocketkotlin.syntax.parser.kotlin

import com.itdroid.pocketkotlin.syntax.model.*
import com.itdroid.pocketkotlin.syntax.parser.kotlin.KotlinParser.*
import com.itdroid.pocketkotlin.utils.iLog
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.TerminalNode

//internal class KotlinSyntaxExtractor : KotlinParserBaseListener() {
internal class KotlinSyntaxExtractor : KotlinParserLoggerImplementation() {

    private var offset: Int = 0

    val syntax: MutableMap<IntRange, SyntaxMarker> = mutableMapOf()

    fun result(): Map<IntRange, SyntaxMarker> = syntax

    fun reset(offset: Int) {
        this.offset = offset
        syntax.clear()
    }

    override fun enterPropertyDeclaration(ctx: PropertyDeclarationContext?) {
        super.enterPropertyDeclaration(ctx)
        ctx?.variableDeclaration()?.simpleIdentifier()?.run {
            syntax[range()] = PropertyMarker
        }
    }

    override fun enterFunctionDeclaration(ctx: FunctionDeclarationContext?) {
        super.enterFunctionDeclaration(ctx)
        ctx?.simpleIdentifier()?.run {
            syntax[range()] = FunctionMarker
        }
    }

    override fun enterLineStringLiteral(ctx: LineStringLiteralContext?) {
        super.enterLineStringLiteral(ctx)
        ctx?.run {
            syntax[range()] = StrCharLiteralMarker
        }
    }

    override fun enterMultiLineStringLiteral(ctx: MultiLineStringLiteralContext?) {
        super.enterMultiLineStringLiteral(ctx)
        ctx?.run {
            syntax[range()] = StrCharLiteralMarker
        }
    }

    override fun enterLiteralConstant(ctx: LiteralConstantContext?) {
        super.enterLiteralConstant(ctx)
        ctx?.run {
            val child = getChild(0)
            if (child is TerminalNode) {
                when (child.symbol.type) {
                    IntegerLiteral, HexLiteral, BinLiteral, FloatLiteral, DoubleLiteral ->
                        syntax[range()] = NumberMarker

                    CharacterLiteral -> syntax[range()] = StrCharLiteralMarker

                    BooleanLiteral, NullLiteral -> syntax[range()] = KeywordMarker
                }
            }
        }
    }

    override fun visitTerminal(node: TerminalNode?) {
        super.visitTerminal(node)
        if (node == null) return

        iLog(tag = "KotlinParser") { "visitTerminal: ${node.text}" }

        when (node.symbol.type) {
            PACKAGE,
            IMPORT, TYPE_ALIAS,
            SEALED, OPEN, ABSTRACT, FINAL,
            CLASS, INTERFACE, OBJECT, ENUM, DATA, INNER,
            CONSTRUCTOR,
            PUBLIC, PROTECTED, INTERNAL, PRIVATE,
            SUSPEND, OVERRIDE,
            VAL, VAR, VARARG,
            FUN,
            INLINE, NOINLINE, CROSSINLINE,
            RETURN,
            CONTINUE, BREAK,
            AS, IS, IN,
            BY,
            COMPANION,
            IF, ELSE, FOR, WHEN, DO, WHILE,
            THROW, CATCH,
            CONST,
            ->
                syntax[node.range()] = KeywordMarker
        }
    }

    private fun ParserRuleContext.range(): IntRange =
        offset + start.startIndex..offset + stop.stopIndex + 1

    private fun TerminalNode.range(): IntRange =
        offset + symbol.startIndex..offset + symbol.stopIndex + 1
}
