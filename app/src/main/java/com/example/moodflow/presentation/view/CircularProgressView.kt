package com.example.moodflow.presentation.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.example.moodflow.R
import kotlin.math.cos
import kotlin.math.sin

class CircularProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = calculateStrokeWidth(context)
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
    }
    var isAnimating = false
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

    private fun calculateStrokeWidth(context: Context): Float {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val screenWidthDp = pxToDp(displayMetrics.widthPixels, context)
        return when {
            screenWidthDp < 390 -> 40f
            screenWidthDp < 720 -> 60f
            else -> 80f
        }
    }

    private fun pxToDp(px: Int, context: Context): Float {
        return px / context.resources.displayMetrics.density
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
        paint.shader = null
        paint.color = ContextCompat.getColor(context, R.color.black_button)
        canvas.drawCircle(centerX, centerY, radius, paint)
        val startX = centerX + cos(Math.toRadians(gradientAngle.toDouble())) * radius
        val startY = centerY + sin(Math.toRadians(gradientAngle.toDouble())) * radius
        val endX = centerX + cos(Math.toRadians(gradientAngle.toDouble() + 90)) * radius
        val endY = centerY + sin(Math.toRadians(gradientAngle.toDouble() + 90)) * radius

        paint.shader = LinearGradient(
            startX.toFloat(), startY.toFloat(),
            endX.toFloat(), endY.toFloat(),
            intArrayOf(ContextCompat.getColor(context, R.color.black_button), Color.parseColor("#666666")),
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
            isAnimating = true
        }
    }

    fun stopAnimation() {
        animator.cancel()
        isAnimating = false
    }

    fun isAnimationPlaying(): Boolean {
        return isAnimating
    }
}
