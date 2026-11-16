package com.example.tp_11___composant

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.min

class CircularScoreView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var score: Int = 75
        set(value) {
            field = value.coerceIn(0, maxScore)
            invalidate()
        }

    var maxScore: Int = 100
        set(value) {
            field = if (value > 0) value else 100
            invalidate()
        }

    private var ringColor: Int = ContextCompat.getColor(context, android.R.color.darker_gray)
    private var progressColor: Int = ContextCompat.getColor(context, android.R.color.holo_blue_light)
    private var textColor: Int = ContextCompat.getColor(context, android.R.color.black)
    private var textSize: Float = 48f
    private var strokeWidth: Float = 24f

    private val ringPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
    }

    private val rectF = RectF()

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularScoreView)
        score = typedArray.getInt(R.styleable.CircularScoreView_score, score)
        maxScore = typedArray.getInt(R.styleable.CircularScoreView_maxScore, maxScore)
        ringColor = typedArray.getColor(R.styleable.CircularScoreView_ringColor, ringColor)
        progressColor = typedArray.getColor(R.styleable.CircularScoreView_progressColor, progressColor)
        textColor = typedArray.getColor(R.styleable.CircularScoreView_textColor, textColor)
        textSize = typedArray.getDimension(R.styleable.CircularScoreView_textSize, textSize)
        strokeWidth = typedArray.getDimension(R.styleable.CircularScoreView_strokeWidth, strokeWidth)
        typedArray.recycle()

        updatePaints()
    }

    private fun updatePaints() {
        ringPaint.color = ringColor
        ringPaint.strokeWidth = strokeWidth

        progressPaint.color = progressColor
        progressPaint.strokeWidth = strokeWidth

        textPaint.color = textColor
        textPaint.textSize = textSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val defaultSize = (200 * resources.displayMetrics.density).toInt()
        val width = resolveSize(defaultSize, widthMeasureSpec)
        val height = resolveSize(defaultSize, heightMeasureSpec)
        val minDim = min(width, height)
        setMeasuredDimension(minDim, minDim)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val padding = strokeWidth / 2f
        rectF.set(padding, padding, w - padding, h - padding)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw background ring
        canvas.drawArc(rectF, 0f, 360f, false, ringPaint)

        // Draw progress arc
        val sweepAngle = 360f * (score.toFloat() / maxScore.toFloat())
        canvas.drawArc(rectF, -90f, sweepAngle, false, progressPaint)

        // Draw score text in center
        val text = "$score / $maxScore"
        val fontMetrics = textPaint.fontMetrics
        val textHeight = fontMetrics.descent - fontMetrics.ascent
        val textOffset = (textHeight / 2f) - fontMetrics.descent
        val centerY = (height / 2f) + textOffset
        canvas.drawText(text, width / 2f, centerY, textPaint)
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.score = score
        ss.maxScore = maxScore
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)
        score = state.score
        maxScore = state.maxScore
        invalidate()
    }

    private class SavedState : BaseSavedState {
        var score: Int = 0
        var maxScore: Int = 0

        constructor(superState: Parcelable?) : super(superState)
        constructor(parcel: Parcel) : super(parcel) {
            score = parcel.readInt()
            maxScore = parcel.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(score)
            out.writeInt(maxScore)
        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(parcel: Parcel): SavedState {
                    return SavedState(parcel)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}
