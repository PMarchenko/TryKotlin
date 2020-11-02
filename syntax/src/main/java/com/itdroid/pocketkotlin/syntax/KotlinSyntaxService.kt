package com.itdroid.pocketkotlin.syntax

import android.text.Editable
import com.itdroid.pocketkotlin.syntax.model.SyntaxMarker
import com.itdroid.pocketkotlin.syntax.parser.kotlin.KotlinLexer
import com.itdroid.pocketkotlin.syntax.parser.kotlin.KotlinParser
import com.itdroid.pocketkotlin.syntax.parser.kotlin.KotlinSyntaxExtractor
import com.itdroid.pocketkotlin.utils.measureExecutionTime
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker


/**
 * @author itdroid
 */
internal class KotlinSyntaxService : SyntaxService {

    private val extractor = KotlinSyntaxExtractor()

    override fun analyze(program: Editable, range: IntRange): Map<IntRange, SyntaxMarker> =
        measureExecutionTime {
            val input = program.toString().substring(range.first, range.last)
            val lexer = KotlinLexer(CharStreams.fromString(input))
            val parser = KotlinParser(CommonTokenStream(lexer))

            extractor.reset(range.first)
            ParseTreeWalker.DEFAULT.walk(extractor, parser.kotlinFile())
            extractor.result()
        }
}
