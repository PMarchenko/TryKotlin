package com.itdroid.pocketkotlin.syntax.model

/**
 * @author Pavel Marchenko
 */
internal class MappingHolder {

    private var mappingId: Long = -1
    private var mapping: SyntaxMapping? = null

    fun get(mappingId: Long): SyntaxMapping =
        if (this.mappingId == mappingId) {
            mapping ?: createSyntaxMapping()
        } else {
            this.mappingId = mappingId
            createSyntaxMapping()
        }

    private fun createSyntaxMapping(): SyntaxMapping = SyntaxMapping().also { mapping = it }

}
