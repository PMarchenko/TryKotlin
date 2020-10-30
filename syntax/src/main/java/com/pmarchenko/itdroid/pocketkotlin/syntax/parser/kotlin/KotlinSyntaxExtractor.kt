package com.pmarchenko.itdroid.pocketkotlin.syntax.parser.kotlin

import com.itdroid.pocketkotlin.syntax.parser.kotlin.KotlinParser.*
import com.itdroid.pocketkotlin.syntax.parser.kotlin.KotlinParserBaseListener
import com.pmarchenko.itdroid.pocketkotlin.syntax.model.SyntaxInfo
import com.pmarchenko.itdroid.pocketkotlin.utils.eLog
import com.pmarchenko.itdroid.pocketkotlin.utils.iLog
import org.antlr.v4.runtime.tree.TerminalNode

internal class KotlinSyntaxExtractor : KotlinParserBaseListener() {
//internal class KotlinSyntaxExtractor : KotlinParserLoggerImplementation() {

    private lateinit var syntax: SyntaxInfo

    fun reset() {
        syntax = SyntaxInfo()
    }

    fun getSyntaxInfo(): SyntaxInfo = syntax

    override fun enterPropertyDeclaration(ctx: PropertyDeclarationContext?) {
        super.enterPropertyDeclaration(ctx)
        ctx?.variableDeclaration()?.simpleIdentifier()?.run {
            syntax.addPropertyName(start.startIndex, stop.stopIndex + 1)
        }
    }

    override fun enterFunctionDeclaration(ctx: FunctionDeclarationContext?) {
        super.enterFunctionDeclaration(ctx)
        ctx?.simpleIdentifier()?.run {
            syntax.addFunName(start.startIndex, stop.stopIndex + 1)
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
            BooleanLiteral, NullLiteral,
            ->
                syntax.addKeyWord(node.symbol.startIndex, node.symbol.stopIndex + 1)

            IntegerLiteral, HexLiteral, BinLiteral, FloatLiteral, DoubleLiteral ->
                syntax.addNumberLiteral(node.symbol.startIndex, node.symbol.stopIndex + 1)

            LineStrText, MultiLineStrText, CharacterLiteral ->
                syntax.addStrCharLiteral(node.symbol.startIndex, node.symbol.stopIndex + 1)
        }
    }
}
