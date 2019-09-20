package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import android.os.Build
import android.text.Html
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.model.log.InfoLogRecord
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback

/**
 * @author Pavel Marchenko
 */
class HolderDelegateInfoLog(
    viewType: Int,
    private val callback: EditorCallback
) : HolderDelegateLog<InfoLogRecord, HolderDelegateInfoLog.InfoLogViewHolder>(viewType) {

    override fun create(inflater: LayoutInflater, parent: ViewGroup): InfoLogViewHolder {
        return InfoLogViewHolder(inflater.inflate(R.layout.viewholder_log, parent, false), callback)
    }

    class InfoLogViewHolder(itemView: View, callback: EditorCallback) : LogViewHolder<InfoLogRecord>(itemView, callback) {

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
}