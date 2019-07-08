package com.pmarchenko.itdroid.pocketkotlin.editor.adapter

import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.extentions.findView
import com.pmarchenko.itdroid.pocketkotlin.model.log.LogRecord
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Pavel Marchenko
 */
open class LogViewHolder<T : LogRecord>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //todo color to resources
    protected val errorTextColor = "#EC5424"

    private val logView by findView<TextView>(R.id.log_timestamp)
    private val dateFormat: DateFormat

    protected val resources: Resources
        get() = itemView.resources

    init {
        dateFormat = SimpleDateFormat(resources.getString(R.string.logs_date_format), Locale.getDefault())
    }

    @Suppress("UNCHECKED_CAST")
    fun bindView(log: LogRecord) {
        logView.text = prepareText(log as T)
    }

    open fun prepareText(log: T): CharSequence {
        val timestamp = SpannableStringBuilder(dateFormat.format(log.timestamp))
        timestamp.setSpan(StyleSpan(Typeface.BOLD), 0, timestamp.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        timestamp.append(':')
        return timestamp
    }

    protected fun asError(text: CharSequence) = applyColor(text, Color.parseColor(errorTextColor))

    protected fun applyColor(text: CharSequence, color: Int): CharSequence {
        val out = if (text is Spannable) text else SpannableString(text)
        out.setSpan(ForegroundColorSpan(color), 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return out
    }
}