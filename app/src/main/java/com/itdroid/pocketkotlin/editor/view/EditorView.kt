package com.itdroid.pocketkotlin.editor.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.text.InputFilter
import android.util.SparseIntArray
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updatePaddingRelative
import androidx.core.widget.doAfterTextChanged
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.projects.FileMaxLength

/**
 * @author Pavel Marchenko
 */
class EditorView(context: Context) : AppCompatEditText(context) {

    private val realToVirtualLines = SparseIntArray()

    private val lineBarWidth = 32f.dp
    private val sideBarBgPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val lineNumberPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            textSize = 12f.dp
        }

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        filters = arrayOf(InputFilter.LengthFilter(FileMaxLength))
        gravity = Gravity.START
        typeface = ResourcesCompat.getFont(context, R.font.jet_brains_mono_regular)
        lineNumberPaint.typeface = typeface

        updatePaddingRelative(start = paddingStart + lineBarWidth.toInt())

        doAfterTextChanged {
            mapRealToVirtualLines(realToVirtualLines)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        mapRealToVirtualLines(realToVirtualLines)
    }

    fun setSideBarBgColor(color: Int) {
        if (sideBarBgPaint.color != color) {
            sideBarBgPaint.color = color
            invalidate()
        }
    }

    override fun setTextColor(color: Int) {
        lineNumberPaint.color = color
        super.setTextColor(color)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        drawSizeBar(canvas)

        for (index in 0 until realToVirtualLines.size()) {
            val lineVirtual = realToVirtualLines.valueAt(index)

            val baseLine = layout.getLineBaseline(lineVirtual) + paddingTop
            val text = (realToVirtualLines.keyAt(index) + 1).toString()
            val textX = (lineBarWidth - lineNumberPaint.measureText(text) - 5f.dp)
            val textY = baseLine.toFloat() - 2f.dp

            canvas.drawText(text, textX, textY, lineNumberPaint)
        }
    }

    private fun mapRealToVirtualLines(map: SparseIntArray) {
        map.clear()
        layout?.let { layout ->
            val saveLine = { index: Int -> map.append(map.size(), index) }

            saveLine(0)
            text?.forEachIndexed { index, c ->
                if (c == '\n') {
                    saveLine(layout.getLineForOffset(index + 1))
                }
            }
        }
    }

    private fun drawSizeBar(canvas: Canvas) {
        canvas.drawRect(0f, 0f, lineBarWidth, bottom.toFloat(), sideBarBgPaint)
        canvas.drawLine(lineBarWidth, 0f, lineBarWidth, bottom.toFloat(), lineNumberPaint)
    }
}

private val Float.dp
    get() = this * Resources.getSystem().displayMetrics.density
