package com.itdroid.pocketkotlin.syntax.model

import android.text.Editable
import com.itdroid.pocketkotlin.syntax.span.SyntaxSpanFactoryProvider

/**
 * @author itdroid
 */
internal class Syntax {

    private val records: MutableMap<IntRange, Record> = mutableMapOf()

    fun add(range: IntRange, marker: SyntaxMarker, span: Any) {
        records[range] = Record(marker, span)
    }

    fun apply(
        update: Map<IntRange, SyntaxMarker>,
        range: IntRange,
        spanFactory: SyntaxSpanFactoryProvider,
    ): SyntaxDiff {
        val out = SyntaxDiff()

        val updates = update.toMutableMap()
        val rangesIterator = records.iterator()
        while (rangesIterator.hasNext()) {
            val (syntaxRange, syntaxRecord) = rangesIterator.next()

            if (syntaxRange.last < range.first || syntaxRange.first > range.last) continue

            val updateMarker = updates[syntaxRange]
            when {
                updateMarker == null -> {
                    rangesIterator.remove()
                    out.spansToDelete.add(syntaxRecord.span)
                }
                syntaxRecord.marker == updateMarker -> {
                    updates.remove(syntaxRange)
                }
                syntaxRecord.marker != updateMarker -> {
                    val newSpan = spanFactory.factoryFor(updateMarker).create()

                    out.spansToDelete.add(syntaxRecord.span)
                    out.spansToAdd[syntaxRange] = newSpan

                    records[syntaxRange] = Record(updateMarker, newSpan)
                    updates.remove(syntaxRange)
                }
            }
        }

        for ((updateRange, updateMarker) in updates) {
            val newSpan = spanFactory.factoryFor(updateMarker).create()
            records[updateRange] = Record(updateMarker, newSpan)
            out.spansToAdd[updateRange] = newSpan
        }

        return out
    }

    fun sync(program: Editable, diff: SyntaxDiff) {
        val allSpans = program.getSpans(0, program.length, Any::class.java)
        for ((range, record) in records) {
            if (!allSpans.contains(record.span) && diff.spansToAdd[range] != record.span) {
                diff.spansToAdd[range] = record.span
            }
        }
    }

    private data class Record(
        val marker: SyntaxMarker,
        val span: Any,
    )
}
