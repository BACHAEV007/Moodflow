package com.example.moodflow.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.moodflow.R
import com.example.moodflow.uicontent.GradientColor

class GradientCircleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 60f
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
    }

    private val progressMap = mutableMapOf<GradientColor, Float>()
    private var totalProgress = 0f

    private val backgroundPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 60f
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
        color = Color.parseColor("#1A1A1A")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = width
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (width / 2f) - paint.strokeWidth / 2

        canvas.drawCircle(centerX, centerY, radius, backgroundPaint)

        if (progressMap.isEmpty()) return

        var startAngle = -90f

        val normalizedTotal = if (totalProgress > 1f) 1f else totalProgress
        progressMap.forEach { (color, fraction) ->
            val sweepAngle = 360f * (fraction / normalizedTotal)

            paint.shader = LinearGradient(
                centerX - radius, centerY,
                centerX + radius, centerY,
                intArrayOf(color.startColor, color.endColor),
                null,
                Shader.TileMode.CLAMP
            )

            canvas.drawArc(
                centerX - radius, centerY - radius,
                centerX + radius, centerY + radius,
                startAngle, sweepAngle, false, paint
            )

            startAngle += sweepAngle
        }
    }

    fun setProgress(progressMap: Map<GradientColor, Float>) {
        this.progressMap.clear()
        this.progressMap.putAll(progressMap)
        totalProgress = progressMap.values.sum()
        invalidate()
    }

}