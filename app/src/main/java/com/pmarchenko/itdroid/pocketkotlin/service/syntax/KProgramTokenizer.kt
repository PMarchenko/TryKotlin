package com.pmarchenko.itdroid.pocketkotlin.service.syntax

import kotlin.math.max

/**
 * @author Pavel Marchenko
 */
class KProgramTokenizer(private val program: CharSequence) : Iterator<ProgramToken> {

    var offset = 0

    override fun hasNext() = offset < program.length

    override fun next(): ProgramToken {
        var eol = program.indexOf('\n', offset)
        if (eol == offset) {
            offset++
            eol = program.indexOf('\n', offset)
        }
        if (eol < 0) eol = program.length
        val line = program.substring(offset, eol)
        return processLine(line)
    }

    private fun processLine(line: String): ProgramToken {
        val commentLineIndex = line.indexOf("//").takeIf { it >= 0 } ?: Int.MAX_VALUE
        val commentBlockIndex = line.indexOf("/*").takeIf { it >= 0 } ?: Int.MAX_VALUE
        val stringLiteralIndex = line.indexOf("${'"'}${'"'}${'"'}").takeIf { it >= 0 } ?: Int.MAX_VALUE

        val commentLineFirst = commentLineIndex < commentBlockIndex && commentLineIndex < stringLiteralIndex
        val commentBlockFirst = commentBlockIndex < commentLineIndex && commentBlockIndex < stringLiteralIndex
        val stringLiteralFirst = stringLiteralIndex < commentLineIndex && stringLiteralIndex < commentBlockIndex

        return when {
            commentBlockFirst -> handleMultilineToken(line, "/*", "*/")
            stringLiteralFirst -> handleMultilineToken(line, "${'"'}${'"'}${'"'}", "${'"'}${'"'}${'"'}") //"""
            commentLineFirst -> handleLineComment(line)
            line.contains(";") -> handleSemicolon(line)
            line.contains("(") -> handleMultilineToken(line, "(", ")")
            else -> handleLine(line)
        }
    }

    private fun handleMultilineToken(line: String, startToken: String, endToken: String): ProgramToken {
        val mlStart = line.indexOf(startToken)
        return if (mlStart == 0) {
            var mlEnd = program.indexOf(endToken, offset + endToken.length)
            if (mlEnd < 0) {
                mlEnd = program.length
            } else {
                mlEnd += endToken.length
            }
            val token = ProgramToken(offset, program.substring(offset, mlEnd))
            offset = mlEnd
            token
        } else {
            handleLine(line.substring(0, mlStart))
        }
    }

    private fun handleLineComment(line: String): ProgramToken {
        val start = line.indexOf("//")
        return if (start == 0) {
            val token = ProgramToken(offset, line)
            offset += line.length
            token
        } else {
            handleLine(line.substring(0, start))
        }
    }

    private fun handleSemicolon(line: String): ProgramToken {
        val end = line.indexOf(';')
        return handleLine(line.substring(0, end))
    }

    private fun handleLine(ln: String): ProgramToken {
        val initialLength = ln.length
        val line = ln.trimStart()
        val newOffset = offset + initialLength - line.length

        offset += max(1, initialLength)
        return ProgramToken(newOffset, line.trimEnd())
    }
}