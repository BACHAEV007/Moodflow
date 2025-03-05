package com.example.moodflow.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.example.moodflow.R

class ViewPagerIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val radius: Float
    private val spacing: Float
    private val totalCircles = 4
    private var selectedPosition = 0

    init {
        radius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            2f,
            resources.displayMetrics
        )

        spacing = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            4f,
            resources.displayMetrics
        )

        paint.color = ContextCompat.getColor(context, R.color.bottom_nav_color)
    }

    fun setSelectedPosition(position: Int) {
        if (position in 0..totalCircles - 1) {
            selectedPosition = position
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f

        for (i in 0 until totalCircles) {
            val centerY = radius + (radius * 2 + spacing) * i + radius
            paint.color = ContextCompat.getColor(context, R.color.bottom_nav_color)
            canvas.drawCircle(centerX, centerY, radius, paint)
        }

        val selectedCenterY = radius + (radius * 2 + spacing) * selectedPosition + radius
        paint.color = Color.WHITE
        canvas.drawCircle(centerX, selectedCenterY, radius, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val desiredWidth = (radius * 2).toInt()
        val desiredHeight = (radius * 2 * totalCircles + spacing * (totalCircles - 1) + radius * 2).toInt()

        val width = if (widthMode == MeasureSpec.EXACTLY) widthSize else desiredWidth
        val height = if (heightMode == MeasureSpec.EXACTLY) heightSize else desiredHeight

        setMeasuredDimension(width, height)
    }
}