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
import com.pmarchenko.itdroid.pocketkotlin.extentions.dp
import com.pmarchenko.itdroid.pocketkotlin.extentions.findAnyKey
import com.pmarchenko.itdroid.pocketkotlin.extentions.isRtl
import com.pmarchenko.itdroid.pocketkotlin.model.EditorError
import com.pmarchenko.itdroid.pocketkotlin.model.project.ErrorSeverity
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectError
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.repository.KotlinSyntaxRepository
import com.pmarchenko.itdroid.pocketkotlin.utils.TextWatcherAdapter
import kotlin.math.max

/**
 * @author Pavel Marchenko
 */
class EditorView : AppCompatEditText {

    companion object {
        private const val NO_LINE = -1
    }

    private val syntaxRepository = KotlinSyntaxRepository()

    private var initialized: Boolean = false

    private var projectCallback: EditorCallback? = null
    private lateinit var file: ProjectFile
    private val errors = mutableListOf<EditorError>()
    private val errorAreas = mutableMapOf<Int, RectF>()
    private val realToVirtualLines = SparseIntArray()
    private var pendingErrors: Pair<ProjectFile, List<ProjectError>>? = null

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
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
        addTextChangedListener(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable?) {
                mapRealToVirtualLInes(realToVirtualLines)
                if (initialized && !s.isNullOrEmpty()) {
                    errors.clear()
                    analyzeSyntax(s)
                }
            }
        })
        initialized = true
    }

    fun setProjectCallback(callback: EditorCallback) {
        projectCallback = callback
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val callback = projectCallback
        if (callback != null && event != null) {
            val x = event.x + scrollX
            val y = event.y + scrollY
            val touchedLine = errorAreas.findAnyKey(opt = NO_LINE) { it.contains(x, y) }
            if (touchedLine != NO_LINE) {
                if (event.action == MotionEvent.ACTION_UP) {
                    val lineErrors = errors.filter { it.startLine == touchedLine } as ArrayList<EditorError>
                    callback.showErrorDetails(file, touchedLine, lineErrors)
                }
                return true
            }
        }

        return super.onTouchEvent(event)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mapRealToVirtualLInes(realToVirtualLines)
        pendingErrors?.let {
            setErrors(it.first, it.second)
            pendingErrors = null
        } ?: analyzeSyntax(editableText)
    }

    override fun draw(canvas: Canvas?) {
        canvas!!

        drawSizeBar(canvas)

        for (index in 0 until realToVirtualLines.size()) {
            val lineVirtual = realToVirtualLines.valueAt(index)
            var hasError = false
            errors.find { error -> error.startLine == lineVirtual }?.let { error ->
                hasError = true
                val area = errorAreas[lineVirtual]
                area!!
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
        super.draw(canvas)
    }

    private fun mapRealToVirtualLInes(map: SparseIntArray) {
        map.clear()
        layout?.let { layout ->
            var realLine = 0
            map.append(realLine++, 0)
            text?.forEachIndexed { index, c ->
                if (c == '\n') map.append(realLine++, layout.getLineForOffset(index + 1))
            }
        }
    }

    private fun drawSizeBar(canvas: Canvas) {
        val bottom = max(layout.getLineBottom(lineCount - 1) + paddingTop, bottom).toFloat()
        canvas.drawRect(0f, paddingTop.toFloat(), lineBarWidth, bottom, lineBarBgPaint)
        canvas.drawLine(lineBarWidth, paddingTop.toFloat(), lineBarWidth, bottom, lineNumberPaint)
    }

    @Suppress("REDUNDANT_ELSE_IN_WHEN")
    private fun getErrorPaint(errors: List<EditorError>, error: EditorError): Paint =
        when (error.severity) {
            ErrorSeverity.ERROR -> errorMarkerPaint
            ErrorSeverity.WARNING ->
                if (errors.any { it.startLine == error.startLine && it.severity == ErrorSeverity.ERROR }) {
                    errorMarkerPaint
                } else {
                    warningMarkerPaint
                }
            else -> error("Unsupported error severity: ${error.severity}")
        }

    fun setErrors(file: ProjectFile, errors: List<ProjectError>) {
        if (layout == null) {
            pendingErrors = file to errors
            return
        }
        this.file = file
        this.errors.clear()
        errorAreas.clear()
        errors.forEach {
            val startLine = realToVirtualLines.valueAt(it.interval.start.line)
            val start = layout.getLineStart(startLine) + it.interval.start.ch

            val endLine = realToVirtualLines.valueAt(it.interval.end.line)
            val end = layout.getLineStart(endLine) + it.interval.end.ch
            if (start >= 0 && end <= editableText.length) {
                this.errors.add(EditorError(it.message, it.severity, startLine, endLine, start, end))
            }
        }
        this.errors.forEach {
            errorAreas[it.startLine] = RectF()
        }
        analyzeSyntax(editableText)
    }

    private fun analyzeSyntax(text: Editable?) {
        val layout = layout
        if (layout !== null && text != null) {
            syntaxRepository.highlightSyntax(text, errors)
        }
    }

    fun setSelection(selection: Pair<Int, Int>) {
        layout?.let { layout ->
            val position = layout.getLineStart(realToVirtualLines.valueAt(selection.first)) + selection.second
            setSelection(position)
        }
    }
}