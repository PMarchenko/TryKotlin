package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback

/**
 * @author Pavel Marchenko
 */
class HolderDelegateInfoLog(private val callback: EditorCallback) :
    HolderDelegateLog<InfoLogRecord, InfoLogViewHolder>() {

    override fun create(inflater: LayoutInflater, parent: ViewGroup) =
        InfoLogViewHolder(
            inflater.inflate(R.layout.viewholder_log, parent, false),
            callback
        )
}

class InfoLogViewHolder(itemView: View, callback: EditorCallback) :
    LogViewHolder<InfoLogRecord>(itemView, callback) {

    override fun describeLog(log: InfoLogRecord): CharSequence {
        return log.message
            .replace("<errStream>", "<font color=\"$errorTextColor\">")
            .replace("</errStream>", "</font>")
            .replace("\n", "<br>")
            .parseAsHtml()
    }
}