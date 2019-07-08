package com.pmarchenko.itdroid.pocketkotlin.editor.adapter

import android.text.Html
import android.text.SpannableStringBuilder
import android.view.View
import com.pmarchenko.itdroid.pocketkotlin.model.log.InfoLogRecord

/**
 * @author Pavel Marchenko
 */
class InfoLogViewHolder(itemView: View) : LogViewHolder<InfoLogRecord>(itemView) {

    override fun prepareText(log: InfoLogRecord): CharSequence {
        val text = SpannableStringBuilder(super.prepareText(log))
        val logText = log.message
                .replace("<errStream>", "<font color=\"$errorTextColor\">").replace("</errStream>", "</font>")
                .replace("\n", "<br>")
        text.append(Html.fromHtml(logText))
        return text
    }
}