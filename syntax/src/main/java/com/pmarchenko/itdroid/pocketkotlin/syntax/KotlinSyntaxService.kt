package com.pmarchenko.itdroid.pocketkotlin.syntax

import android.text.Editable
import com.itdroid.pocketkotlin.syntax.parser.kotlin.KotlinLexer
import com.itdroid.pocketkotlin.syntax.parser.kotlin.KotlinParser
import com.pmarchenko.itdroid.pocketkotlin.syntax.model.SyntaxInfo
import com.pmarchenko.itdroid.pocketkotlin.syntax.parser.kotlin.KotlinSyntaxExtractor
import com.pmarchenko.itdroid.pocketkotlin.utils.measureExecutionTime
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker


/**
 * @author Pavel Marchenko
 */
internal class KotlinSyntaxService : SyntaxService {

    private val lexer = KotlinLexer(null)
    private val parser = KotlinParser(null)
    private val extractor = KotlinSyntaxExtractor()

    override fun analyze(program: Editable): SyntaxInfo =
        measureExecutionTime {
            lexer.inputStream = CharStreams.fromString(program.toString())
            parser.inputStream = CommonTokenStream(lexer)

            extractor.reset()
            ParseTreeWalker.DEFAULT.walk(extractor, parser.kotlinFile())
            extractor.getSyntaxInfo()
        }
}
