package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.text.Editable
import android.util.AttributeSet
import android.util.SparseIntArray
import android.view.MotionEvent
import androidx.appcompat.R
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.updatePaddingRelative
import androidx.core.widget.doAfterTextChanged
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ErrorSeverity
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectError
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.syntax.KotlinSyntaxRepository
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.model.EditorError
import com.pmarchenko.itdroid.pocketkotlin.utils.dp
import kotlin.math.max

/**
 * @author Pavel Marchenko
 */
class EditorView : AppCompatEditText {

    private val syntaxRepository = KotlinSyntaxRepository()

    private var projectCallback: EditorCallback? = null
    private lateinit var file: ProjectFile
    private val errors = mutableListOf<EditorError>()
    private val errorAreas = mutableMapOf<Int, RectF>()
    private val realToVirtualLines = SparseIntArray()
    private var pendingErrors: Pair<ProjectFile, List<ProjectError>>? = null

    var handleTouchEvents = true

    val program
        get() = text.toString()

    private var lineBarWidth = 32f.dp
    private var lineBarBgPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#313335")
    }

    private var lineNumberPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#656E76")
        textSize = 12f.dp
        typeface = Typeface.MONOSPACE
    }

    private var errorMarkerPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#BBEC5424")
    }

    private var warningMarkerPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#BBFFC107")
    }

    constructor(context: Context) : this(context, null, R.attr.editTextStyle)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.editTextStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    init {
        updatePaddingRelative(
            start = paddingStart + lineBarWidth.toInt()
        )
        doAfterTextChanged {
            mapRealToVirtualLines(realToVirtualLines)
            if (!it.isNullOrEmpty()) {
                errors.clear()
                analyzeSyntax(it)
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        return if (handleTouchEvents) super.dispatchTouchEvent(event) else false
    }

    fun setProjectCallback(callback: EditorCallback) {
        projectCallback = callback
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val callback = projectCallback
        if (callback != null && event != null && event.action == MotionEvent.ACTION_UP) {
            val x = event.x + scrollX
            val y = event.y + scrollY

            errorAreas.filterValues { it.contains(x, y) }
                .map { it.key }
                .firstOrNull()
                ?.let { touchedLine ->
                    val lineErrors =
                        this.errors.filterTo(ArrayList()) { it.startLine == touchedLine }
                    callback.showErrorDetails(file, touchedLine, lineErrors)
                    return true
                }
        }
        return super.onTouchEvent(event)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mapRealToVirtualLines(realToVirtualLines)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        drawSizeBar(canvas)

        for (index in 0 until realToVirtualLines.size()) {
            val lineVirtual = realToVirtualLines.valueAt(index)
            var hasError = false
            errors.find { error -> error.startLine == lineVirtual }?.let { error ->
                hasError = true
                val area = errorAreas[lineVirtual]
                area ?: return@let

                if (area.isEmpty) {
                    area.left = 2f.dp
                    area.right = lineBarWidth.toInt() - 2f.dp
                    area.top = layout.getLineTop(lineVirtual) + 2f.dp
                    area.bottom = layout.getLineBottom(lineVirtual) - 2f.dp
                }
                canvas.drawRoundRect(area, 4f.dp, 4f.dp, getErrorPaint(errors, error))
            }

            val baseLine = layout.getLineBaseline(lineVirtual) + paddingTop
            val text = (realToVirtualLines.keyAt(index) + 1).toString()
            val textX = (lineBarWidth - lineNumberPaint.measureText(text) - 5f.dp)
            val textY = baseLine.toFloat() - 2f.dp

            lineNumberPaint.color = if (hasError) Color.WHITE else Color.parseColor("#656E76")
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
        val bottom = max(layout.getLineBottom(lineCount - 1) + paddingTop, bottom).toFloat()
        canvas.drawRoundRect(0f, 0f, lineBarWidth, bottom, 4f.dp, 4f.dp, lineBarBgPaint)
        canvas.drawLine(lineBarWidth, 0f, lineBarWidth, bottom, lineNumberPaint)
    }

    private fun getErrorPaint(errors: List<EditorError>, error: EditorError): Paint =
        when (error.severity) {
            ErrorSeverity.ERROR -> errorMarkerPaint
            ErrorSeverity.WARNING ->
                if (
                    errors.any {
                        it.startLine == error.startLine && it.severity == ErrorSeverity.ERROR
                    }
                ) {
                    errorMarkerPaint
                } else {
                    warningMarkerPaint
                }
        }

    fun setErrors(file: ProjectFile, errors: List<ProjectError>) {
        if (layout == null) {
            pendingErrors = file to errors
            return
        }
        this.file = file
        this.errors.clear()
        errorAreas.clear()

        for (error in errors) {
            val startLine = realToVirtualLines.valueAt(error.interval.start.line)
            val start = layout.getLineStart(startLine) + error.interval.start.ch

            val endLine = realToVirtualLines.valueAt(error.interval.end.line)
            val end = layout.getLineStart(endLine) + error.interval.end.ch
            if (start >= 0 && end <= editableText.length) {
                val editorError =
                    EditorError(
                        error.message,
                        error.severity,
                        startLine,
                        endLine,
                        start,
                        end
                    )
                this.errors.add(editorError)
                errorAreas[editorError.startLine] = RectF()
            }
        }

        analyzeSyntax(editableText)
        invalidate()
    }

    private fun analyzeSyntax(text: Editable?) {
        val layout = layout
        if (layout !== null && text != null) {
            syntaxRepository.highlightSyntax(text)
        }
    }

    fun setSelection(selection: Pair<Int, Int>) {
        layout?.let { layout ->
            val position =
                layout.getLineStart(realToVirtualLines.valueAt(selection.first)) + selection.second
            setSelection(position)
        }
    }
}