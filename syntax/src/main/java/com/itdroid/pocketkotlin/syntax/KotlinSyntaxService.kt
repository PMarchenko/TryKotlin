package com.itdroid.pocketkotlin.syntax

import android.text.Editable
import com.itdroid.pocketkotlin.syntax.model.SyntaxToken
import com.itdroid.pocketkotlin.syntax.parser.kotlin.KotlinLexer
import com.itdroid.pocketkotlin.syntax.parser.kotlin.KotlinParser
import com.itdroid.pocketkotlin.utils.measureExecutionTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import kotlin.system.measureTimeMillis


/**
 * @author itdroid
 */
internal class KotlinSyntaxService : SyntaxService {

    private val processor = KotlinSyntaxProcessor()

    private val lexer = KotlinLexer(null)
    private val parser = KotlinParser(null)

    override suspend fun analyze(program: Editable): Flow<SyntaxToken> =
        flow {
            warmup()
            val tree = parseTree(program.toString())
            processor.process(this, tree)
        }

    private fun warmup() {
        parseTree("")
    }

    private fun parseTree(program: String): KotlinParser.KotlinFileContext {
        lexer.inputStream = measureExecutionTime(msg = "lexer") {
            CharStreams.fromString(program)
        }
        parser.inputStream = measureExecutionTime(msg = "parser") {
            CommonTokenStream(lexer)
        }

        return measureExecutionTime(msg = "tree") {
            parser.kotlinFile()
        }
    }
}
