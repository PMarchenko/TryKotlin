package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.annotation.StringRes
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.databinding.ViewholderLogBinding
import com.pmarchenko.itdroid.pocketkotlin.formatTimestamp
import com.pmarchenko.itdroid.pocketkotlin.syntax.ColorUnderlineSpan
import com.pmarchenko.itdroid.pocketkotlin.ui.ClickableSpan
import com.pmarchenko.itdroid.pocketkotlin.ui.ClickableSpanListener
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import kotlinx.android.extensions.LayoutContainer
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Pavel Marchenko
 */
open class LogViewHolder<T : LogRecord>(
    override val containerView: View,
    protected val callback: EditorCallback
) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val binding = ViewholderLogBinding.bind(containerView)

    //todo color to resources
    protected val errorTextColor = Color.parseColor("#BBEC5424")
    protected val linkUnderlineTextColor = Color.parseColor("#2196F3")

    private val dateFormat: SimpleDateFormat

    init {
        dateFormat = SimpleDateFormat(getString(R.string.logs__date_format), Locale.getDefault())
        binding.logMessage.movementMethod = LinkMovementMethod.getInstance()
    }

    fun bindView(log: LogRecord) {
        @Suppress("UNCHECKED_CAST")
        binding.logMessage.text = prepareText(log as T)
    }

    protected open fun prepareText(log: T): CharSequence {
        return SpannableStringBuilder()
            .bold { append(dateFormat.formatTimestamp(log.timestamp)) }
            .append(':')
            .append(describeLog(log))
    }

    protected open fun describeLog(log: T): CharSequence = ""

    protected fun error(text: CharSequence): CharSequence {
        val out = SpannableStringBuilder.valueOf(text)
        out.setSpan(
            ForegroundColorSpan(errorTextColor),
            0,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return out
    }

    protected fun <D> link(
        text: CharSequence,
        data: D,
        callback: ClickableSpanListener<D>,
        underlineColor: Int = linkUnderlineTextColor
    ): SpannableStringBuilder {
        val out = SpannableStringBuilder.valueOf(text)
        out.setSpan(ClickableSpan(data, callback), 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        out.setSpan(
            ColorUnderlineSpan(underlineColor),
            0,
            text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return out
    }

    protected fun getString(@StringRes id: Int, vararg args: Any) =
        itemView.resources.getString(id, *args)

}