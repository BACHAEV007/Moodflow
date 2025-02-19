package com.example.moodflow.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.cos
import kotlin.math.sin

class CircularProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 60f
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
    }

    private var gradientAngle = 0f

    private val animator = ValueAnimator.ofFloat(0f, 360f).apply {
        duration = 5000
        repeatCount = ValueAnimator.INFINITE
        interpolator = LinearInterpolator()
        addUpdateListener {
            gradientAngle = it.animatedValue as Float
            invalidate()
        }
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
        val radius = (width / 2f) - paint.strokeWidth
        paint.shader = null
        paint.color = Color.parseColor("#1A1A1A")
        canvas.drawCircle(centerX, centerY, radius, paint)
        val startX = centerX + cos(Math.toRadians(gradientAngle.toDouble())) * radius
        val startY = centerY + sin(Math.toRadians(gradientAngle.toDouble())) * radius
        val endX = centerX + cos(Math.toRadians(gradientAngle.toDouble() + 90)) * radius
        val endY = centerY + sin(Math.toRadians(gradientAngle.toDouble() + 90)) * radius

        paint.shader = LinearGradient(
            startX.toFloat(), startY.toFloat(),
            endX.toFloat(), endY.toFloat(),
            intArrayOf(Color.parseColor("#1A1A1A"), Color.parseColor("#666666")),
            null,
            Shader.TileMode.CLAMP
        )

        canvas.drawArc(
            centerX - radius, centerY - radius,
            centerX + radius, centerY + radius,
            gradientAngle, 90f, false, paint
        )
    }

    fun startAnimation() {
        if (!animator.isRunning) {
            animator.start()
        }
    }

    fun stopAnimation() {
        animator.cancel()
    }
}
