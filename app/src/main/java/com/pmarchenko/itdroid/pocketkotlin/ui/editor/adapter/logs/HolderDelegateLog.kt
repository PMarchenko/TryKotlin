package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.HolderDelegate

/**
 * @author Pavel Marchenko
 */
abstract class HolderDelegateLog<L : LogRecord, VH : LogViewHolder<L>> :
    HolderDelegate<VH, LogContentData>() {

    override fun bind(holder: VH, position: Int, data: LogContentData) {
        holder.bindView(data.log)
    }
}