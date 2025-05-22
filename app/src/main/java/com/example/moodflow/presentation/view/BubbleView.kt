package com.example.moodflow.presentation.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.example.moodflow.R
import kotlin.math.cos
import kotlin.math.sin

class  BubbleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private var animator: ValueAnimator
    private val gradients = listOf(
        GradientInfo(0f, 0f, 1500f, ContextCompat.getColor(context, R.color.green_card_text),  ContextCompat.getColor(context, R.color.green_card_text_transparent)),
        GradientInfo(0f, 0f, 1500f,  ContextCompat.getColor(context, R.color.red_card_text),  ContextCompat.getColor(context, R.color.red_card_text_transparent)),
        GradientInfo(0f, 0f, 1500f,  ContextCompat.getColor(context, R.color.yellow_card_text),  ContextCompat.getColor(context, R.color.yellow_card_text_transparent)),
        GradientInfo(0f, 0f, 1500f,  ContextCompat.getColor(context, R.color.blue_card_text),  ContextCompat.getColor(context, R.color.blue_card_text_transparent))
    )
    private var animationProgress = 0f

    init {
        animator = ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 10000
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener { animator ->
                animationProgress = animator.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = width / 3f

        gradients.forEachIndexed { index, gradient ->
            val angle = Math.toRadians((animationProgress + index * 90) % 360.0)
            val x = (centerX + radius * cos(angle)).toFloat()
            val y = (centerY + radius * sin(angle)).toFloat()

            val shader = RadialGradient(
                x, y, gradient.radius,
                intArrayOf(gradient.startColor, gradient.endColor), null,
                Shader.TileMode.CLAMP
            )
            paint.shader = shader

            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        }
    }

    data class GradientInfo(
        var centerX: Float,
        var centerY: Float,
        var radius: Float,
        var startColor: Int,
        var endColor: Int,
        var percent: Int = 0
    )

    fun startAnimation() {
        if (!animator.isRunning) animator.start()
    }

    fun stopAnimation() {
        animator.cancel()
    }
}
