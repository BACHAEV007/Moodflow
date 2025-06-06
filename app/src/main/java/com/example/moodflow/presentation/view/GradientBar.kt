package com.example.moodflow.presentation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.moodflow.R

class GradientBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var barLength: Float = 0f
    private var barColorStart: Int = Color.GREEN
    private var barColorEnd: Int = ContextCompat.getColor(context, R.color.bottom_nav_color)

    fun setBarParams(length: Float, startColorRes: Int, endColorRes: Int) {
        barLength = length.coerceIn(0f, 1f)
        barColorStart = getColorFromResource(startColorRes)
        barColorEnd = getColorFromResource(endColorRes)
        invalidate()
    }

    private fun getColorFromResource(colorRes: Int): Int {
        return try {
            ContextCompat.getColor(context, colorRes)
        } catch (e: Exception) {
            Color.GRAY
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val barHeight = height
        val strokeWidth = barHeight
        val halfStroke = strokeWidth / 2
        val effectiveBarLength = (barLength * width).coerceIn(halfStroke, width - halfStroke)

        val shader = LinearGradient(
            0f, height / 2,
            effectiveBarLength, height / 2,
            intArrayOf(barColorStart, barColorEnd),
            null,
            Shader.TileMode.CLAMP
        )
        paint.shader = shader
        paint.strokeWidth = strokeWidth
        paint.strokeCap = Paint.Cap.ROUND

        val startX = halfStroke
        val endX = effectiveBarLength
        canvas.drawLine(startX, height / 2, endX, height / 2, paint)
    }
}