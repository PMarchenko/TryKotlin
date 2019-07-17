package com.pmarchenko.itdroid.pocketkotlin.service.syntax

import android.text.Editable
import android.text.Spanned
import com.pmarchenko.itdroid.pocketkotlin.model.EditorError
import com.pmarchenko.itdroid.pocketkotlin.model.project.ErrorSeverity
import com.pmarchenko.itdroid.pocketkotlin.service.syntax2.KProgramTokenizer2
import com.pmarchenko.itdroid.pocketkotlin.utils.logi
import com.pmarchenko.itdroid.pocketkotlin.utils.measure

/**
 * @author Pavel Marchenko
 */
class KotlinSyntaxService {

    companion object {
        val KEYWORDS =
                listOf(
                        "package",
                        "import",
                        "fun", "var", "val",
                        "companion", "object",
                        "data", "class", "interface",
                        "while", "for", "if",
                        "private", "public", "protected",
                        "static", "final",
                        "true", "false", "null")
    }

    fun highlightSyntax(program: Editable, errors: MutableList<EditorError>) {
        measure("KProgramTokenizer2") {
            highlightSyntaxInternal(program, errors)
        }
    }

    private fun highlightSyntaxInternal(program: Editable, errors: MutableList<EditorError>) {
        val syntax = prepareSyntaxSpans(program, errors)
        //todo optimization: work with spans diff
        clearOldSyntax(program)
        applyNewSyntax(program, syntax)
    }

    private fun prepareSyntaxSpans(program: CharSequence, errors: MutableList<EditorError>): Syntax {
        val out = Syntax()

        errors.forEach { error -> out.add(getSpanForError(error, error.start, error.end)) }

        val tokenizer2 = KProgramTokenizer2(program.toString())
        while (tokenizer2.hasNext()) {
            val token = tokenizer2.next()
            val text = token.text
            val delimiter = token.delim
            val start = token.offset
            val end = start + text.length

            logi(tag = "KProgramTokenizer", msg = "$start: '$text' + '$delimiter'")

            when {
                KEYWORDS.contains(text) -> out.add(KeywordSpan(start, end))
                delimiter == "/*" -> {
                    val commentEnd = consumeTillDelimiter(tokenizer2, "*/")
                    val comment = program.substring(start, commentEnd)
                    val isJavaDocComment = comment.startsWith("/**") && comment != "/**/"
                    out.add(if (isJavaDocComment) JavaDocComment(end, commentEnd) else Comment(end, commentEnd))
                }
                delimiter == "//" -> out.add(Comment(end, consumeTillDelimiter(tokenizer2, "\n")))
                else -> out.add(TestHighlight(start, end))
            }
        }

        return out
    }

    private fun consumeTillDelimiter(tokenizer: KProgramTokenizer2, delimiter: String): Int {
        while (tokenizer.hasNext()) {
            val token = tokenizer.next()
            if (token.delim == delimiter) return token.offset + token.text.length + delimiter.length
        }
        return tokenizer.offset
    }

    private fun clearOldSyntax(program: Editable) {
        val oldSpans = program.getSpans(0, program.length, Syntax::class.java)
        oldSpans.forEach { syntax ->
            syntax.forEach { syntaxToken ->
                program.removeSpan(syntaxToken.span)
            }
            program.removeSpan(syntax)
        }
    }

    private fun applyNewSyntax(program: Editable, spans: Syntax) {
        spans.forEach {
            program.setSpan(it.span, it.start, it.end, it.flags)
        }
        program.setSpan(spans, 0, 0, Spanned.SPAN_MARK_MARK)
    }

    @Suppress("REDUNDANT_ELSE_IN_WHEN")
    private fun getSpanForError(error: EditorError, start: Int, end: Int) = when (error.severity) {
        ErrorSeverity.ERROR -> ErrorSpan(start, end)
        ErrorSeverity.WARNING -> WarningSpan(start, end)
        else -> error("Unsupported error severity: ${error.severity}")
    }
}