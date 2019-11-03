package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.core.extentions.bindView
import com.pmarchenko.itdroid.pocketkotlin.core.extentions.formatTimestamp
import com.pmarchenko.itdroid.pocketkotlin.core.utils.ClickableSpan
import com.pmarchenko.itdroid.pocketkotlin.core.utils.ClickableSpanListener
import com.pmarchenko.itdroid.pocketkotlin.data.model.log.LogRecord
import com.pmarchenko.itdroid.pocketkotlin.syntax.ColorUnderlineSpan
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Pavel Marchenko
 */
open class LogViewHolder<T : LogRecord>(
    itemView: View,
    protected val callback: EditorCallback
) :
    RecyclerView.ViewHolder(itemView) {

    //todo color to resources
    protected val errorTextColor = Color.parseColor("#BBEC5424")
    protected val linkUnderlineTextColor = Color.parseColor("#2196F3")

    private val logView by bindView<TextView>(R.id.logMessage)
    private val dateFormat: SimpleDateFormat

    protected val resources: Resources
        get() = itemView.resources

    init {
        dateFormat =
            SimpleDateFormat(resources.getString(R.string.logs__date_format), Locale.getDefault())
        logView.movementMethod = LinkMovementMethod.getInstance()
    }

    @Suppress("UNCHECKED_CAST")
    fun bindView(log: LogRecord) {
        logView.text = prepareText(log as T)
    }

    open fun prepareText(log: T): CharSequence {
        val timestamp = SpannableStringBuilder(dateFormat.formatTimestamp(log.timestamp))
        timestamp.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            timestamp.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        timestamp.append(':')
        return timestamp
    }

    protected fun asError(text: CharSequence) = asColoredText(text, errorTextColor)

    protected fun <D> asLink(
        text: CharSequence,
        data: D,
        callback: ClickableSpanListener<D>,
        underlineColor: Int
    ): CharSequence {
        val out = if (text is Spannable) text else SpannableString(text)
        out.setSpan(ClickableSpan(data, callback), 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        out.setSpan(
            ColorUnderlineSpan(underlineColor),
            0,
            text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return out
    }

    protected fun asColoredText(text: CharSequence, color: Int): CharSequence {
        val out = if (text is Spannable) text else SpannableString(text)
        out.setSpan(ForegroundColorSpan(color), 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return out
    }
}