package com.pmarchenko.itdroid.pocketkotlin.service.syntax2

/**
 * @author Pavel Marchenko
 */
class KProgramTokenizer2(private val program: String) : Iterator<ProgramToken2> {

    private val delimiters = listOf(
            "\n",
            "\"",
            "//", "/*", "*/",
            ";",
            "=",
            "(",
            ")",
            ",",
            "{",
            "}",
            "'",
            " "
    )

    var offset: Int = 0

    override fun hasNext() = offset < program.length

    override fun next(): ProgramToken2 {
        val entry = delimiters.associateWith { delimiter -> program.indexOf(delimiter, offset) }
                .filter { it.value >= 0 }
                .minBy { it.value }
        if (entry == null) {
            val token = handleText(program.substring(offset), "")
            offset = program.length
            return token
        }

        val index = entry.value
        val delimiter = entry.key

        val text = program.substring(offset, index)
        val token = handleText(text, delimiter)
        offset += text.length + delimiter.length
        return token
    }

    private fun handleText(text: String, delimiter: String): ProgramToken2 {
        val length = text.length
        val localText = text.trimStart()
        val textOffset = offset + length - localText.length
        return ProgramToken2(textOffset, localText.trimEnd(), delimiter)
    }
}