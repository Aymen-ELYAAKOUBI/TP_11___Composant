package com.example.tp_11___composant

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.min

class RatingBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var rating: Int = 3
        set(value) {
            field = value.coerceIn(0, maxRating)
            invalidate()
            onRatingChangedListener?.invoke(field)
        }

    var maxRating: Int = 5
        set(value) {
            field = if (value > 0) value else 5
            invalidate()
        }

    var onRatingChangedListener: ((Int) -> Unit)? = null

    private var starColorActive: Int = ContextCompat.getColor(context, android.R.color.holo_orange_dark)
    private var starColorInactive: Int = ContextCompat.getColor(context, android.R.color.darker_gray)

    private val paintActive = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = starColorActive
    }

    private val paintInactive = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = starColorInactive
    }

    private val starPath = Path()

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.RatingBarView)
        maxRating = ta.getInt(R.styleable.RatingBarView_maxRating, maxRating)
        rating = ta.getInt(R.styleable.RatingBarView_rating, rating)
        starColorActive = ta.getColor(R.styleable.RatingBarView_starColorActive, starColorActive)
        starColorInactive = ta.getColor(R.styleable.RatingBarView_starColorInactive, starColorInactive)
        ta.recycle()

        paintActive.color = starColorActive
        paintInactive.color = starColorInactive
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val defaultSizeHeight = (48 * resources.displayMetrics.density).toInt()
        val defaultSizeWidth = defaultSizeHeight * maxRating

        val width = resolveSize(defaultSizeWidth, widthMeasureSpec)
        val height = resolveSize(defaultSizeHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val starWidth = width.toFloat() / maxRating

        for (i in 0 until maxRating) {
            canvas.save()
            val offsetX = i * starWidth + (starWidth / 2f)
            val offsetY = height / 2f
            val scale = min(starWidth, height.toFloat()) / 2f * 0.8f

            canvas.translate(offsetX, offsetY)
            canvas.scale(scale, scale)

            createStarPath(starPath)
            val paint = if (i < rating) paintActive else paintInactive
            canvas.drawPath(starPath, paint)

            canvas.restore()
        }
    }

    private fun createStarPath(path: Path) {
        path.reset()
        val points = 5
        val innerRadius = 0.4f
        val outerRadius = 1.0f

        for (i in 0 until (points * 2)) {
            val radius = if (i % 2 == 0) outerRadius else innerRadius
            val angle = Math.PI * i / points - Math.PI / 2
            val x = (radius * kotlin.math.cos(angle)).toFloat()
            val y = (radius * kotlin.math.sin(angle)).toFloat()

            if (i == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }
        path.close()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) {
            val starWidth = width.toFloat() / maxRating
            val clickedIndex = (event.x / starWidth).toInt() + 1
            rating = clickedIndex.coerceIn(0, maxRating)
            return true
        }
        return super.onTouchEvent(event)
    }
}
