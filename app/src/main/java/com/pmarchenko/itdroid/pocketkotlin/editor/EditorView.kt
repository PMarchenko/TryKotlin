package com.pmarchenko.itdroid.pocketkotlin.editor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.R
import androidx.appcompat.widget.AppCompatEditText
import com.pmarchenko.itdroid.pocketkotlin.extentions.dp
import com.pmarchenko.itdroid.pocketkotlin.extentions.isRtl

/**
 * @author Pavel Marchenko
 */
class EditorView : AppCompatEditText {

    private var lineBarWidth = 24f.dp()
    private var lineBarBgPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.LTGRAY
    }

    private var lineNumberPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.DKGRAY
        textSize = 12f.dp()
        typeface = Typeface.MONOSPACE
    }

    constructor(context: Context) : this(context, null, R.attr.editTextStyle)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.editTextStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)

    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        setPaddingRelative(
                if (isRtl()) right else left,
                top,
                if (isRtl()) left else right,
                bottom
        )
    }

    override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
        super.setPaddingRelative(start + lineBarWidth.toInt(), top, end, bottom)
    }

    override fun draw(canvas: Canvas?) {
        if (canvas == null) return

        canvas.drawRect(
                0f,
                paddingTop.toFloat(),
                lineBarWidth,
                layout.getLineBottom(lineCount - 1).toFloat() + paddingTop,
                lineBarBgPaint
        )

        for (line in 0 until lineCount) {
            val baseLine = layout.getLineBaseline(line) + paddingTop
            val text = (line + 1).toString()
            canvas.drawText(text, (lineBarWidth - lineNumberPaint.measureText(text) - 4f.dp()), baseLine.toFloat() - 2f.dp(), lineNumberPaint)
        }

        super.draw(canvas)
    }
}