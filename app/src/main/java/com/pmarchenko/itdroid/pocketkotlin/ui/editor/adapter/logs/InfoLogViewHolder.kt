package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import android.os.Build
import android.text.Html
import android.text.SpannableStringBuilder
import android.view.View
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import com.pmarchenko.itdroid.pocketkotlin.model.log.InfoLogRecord

/**
 * @author Pavel Marchenko
 */
class InfoLogViewHolder(itemView: View, callback: EditorCallback) :
        LogViewHolder<InfoLogRecord>(itemView, callback) {

    override fun prepareText(log: InfoLogRecord): CharSequence {
        val text = SpannableStringBuilder(super.prepareText(log))
        val logText = log.message
                .replace("<errStream>", "<font color=\"$errorTextColor\">").replace("</errStream>", "</font>")
                .replace("\n", "<br>")
        val html = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(logText, Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(logText)
        }
        text.append(html)
        return text
    }
}