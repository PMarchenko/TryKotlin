package com.pmarchenko.itdroid.pocketkotlin.editor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.R
import androidx.appcompat.widget.AppCompatEditText
import com.pmarchenko.itdroid.pocketkotlin.extentions.dp
import com.pmarchenko.itdroid.pocketkotlin.extentions.findAnyKey
import com.pmarchenko.itdroid.pocketkotlin.extentions.isRtl
import com.pmarchenko.itdroid.pocketkotlin.model.ErrorSeverity
import com.pmarchenko.itdroid.pocketkotlin.model.ProjectError
import com.pmarchenko.itdroid.pocketkotlin.service.KotlinSyntaxService

/**
 * @author Pavel Marchenko
 */
class EditorView : AppCompatEditText {

    companion object {
        private const val NO_LINE = -1
    }

    private val syntaxService: KotlinSyntaxService? = KotlinSyntaxService()
    private var pendingSyntaxAnalyze = false

    private var fileName: String = ""
    private var projectCallback: ProjectCallback? = null
    private val errors = mutableListOf<ProjectError>()
    private val errorAreas = mutableMapOf<Int, RectF>()

    private var lineBarWidth = 32f.dp
    private var lineBarBgPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.LTGRAY
    }

    private var lineNumberPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.DKGRAY
        textSize = 12f.dp
        typeface = Typeface.MONOSPACE
    }

    private var errorMarkerPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#EC5424")
    }

    private var warningMarkerPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FFC107")
    }

    constructor(context: Context) : this(context, null, R.attr.editTextStyle)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.editTextStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    fun setProjectCallback(callback: ProjectCallback) {
        this.projectCallback = callback
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        setPaddingRelative(
                if (isRtl()) right else left,
                top,
                if (isRtl()) left else right,
                bottom)
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        @Suppress("UNNECESSARY_SAFE_CALL")
        errors?.let { errors.clear() }
        analyzeSyntax()
    }

    override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
        super.setPaddingRelative(start + lineBarWidth.toInt(), top, end, bottom)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if(pendingSyntaxAnalyze) {
            pendingSyntaxAnalyze = false
            analyzeSyntax()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (projectCallback != null && event != null) {
            val x = event.x
            val y = event.y
            val touchedLine = findLine(x, y)
            if (touchedLine != NO_LINE) {
                if (event.action == MotionEvent.ACTION_UP) {
                    val lineErrors = errors.filter { it.interval.start.line == touchedLine } as ArrayList<ProjectError>
                    projectCallback?.showErrorDetails(fileName, touchedLine, lineErrors)
                }
                return true
            }
        }

        return super.onTouchEvent(event)
    }

    private fun findLine(x: Float, y: Float) = errorAreas.findAnyKey(NO_LINE) { it.contains(x, y) }

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
            errors.forEach { error ->
                if (error.interval.start.line == line) {
                    val area = errorAreas[line]
                    area!!
                    if (area.isEmpty) {
                        area.left = 2f.dp
                        area.right = lineBarWidth.toInt() - 2f.dp
                        area.top = layout.getLineTop(line) + 2f.dp
                        area.bottom = layout.getLineBottom(line) - 2f.dp
                    }
                    canvas.drawRoundRect(area, 4f.dp, 4f.dp, getErrorPaint(errors, error))
                }
            }

            val baseLine = layout.getLineBaseline(line) + paddingTop
            val text = (line + 1).toString()
            val textX = (lineBarWidth - lineNumberPaint.measureText(text) - 5f.dp)
            val textY = baseLine.toFloat() - 2f.dp

            canvas.drawText(text, textX, textY, lineNumberPaint)
        }

        super.draw(canvas)
    }

    @Suppress("REDUNDANT_ELSE_IN_WHEN")
    private fun getErrorPaint(errors: List<ProjectError>, error: ProjectError): Paint =
            when (error.severity) {
                ErrorSeverity.ERROR -> errorMarkerPaint
                ErrorSeverity.WARNING ->
                    if (errors.any { it.interval.start.line == error.interval.start.line && it.severity == ErrorSeverity.ERROR }) {
                        errorMarkerPaint
                    } else {
                        warningMarkerPaint
                    }
                else -> error("Unsupported error severity: ${error.severity}")
            }

    fun setErrors(fileName: String, errors: List<ProjectError>) {
        if (this.errors != errors) {
            this.fileName = fileName
            this.errors.clear()
            this.errors.addAll(errors)
            errorAreas.clear()
            this.errors.forEach {
                errorAreas[it.interval.start.line] = RectF()
            }

            invalidate()
            analyzeSyntax()
        }
    }

    private fun analyzeSyntax() {
        val services = syntaxService
        val layout = layout
        if (services !== null && layout !== null) {
            services.highlightSyntax(editableText, layout, errors)
        } else {
            pendingSyntaxAnalyze = true
        }
    }
}