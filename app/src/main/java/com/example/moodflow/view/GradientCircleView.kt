package com.example.moodflow.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.example.moodflow.R
import com.example.moodflow.uicontent.GradientColor
import kotlin.math.cos
import kotlin.math.sin

class GradientCircleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var point = 2
    private val progressMap = mutableMapOf<GradientColor, Float>()
    private var totalProgress = 0f

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = calculateStrokeWidth(context)
        strokeCap = if (totalProgress != 100f) Paint.Cap.ROUND else Paint.Cap.BUTT
        isAntiAlias = true
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


    private val backgroundPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = calculateStrokeWidth(context)
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
        color = ContextCompat.getColor(context, R.color.black_button)
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

        progressMap.forEach { (color, fraction) ->
            val sweepAngle = 360f * fraction
            val startX = centerX + cos(Math.toRadians(startAngle.toDouble())) * radius
            val startY = centerY + sin(Math.toRadians(startAngle.toDouble())) * radius
            val endX = centerX + cos(Math.toRadians(sweepAngle.toDouble())) * radius
            val endY = centerY + sin(Math.toRadians(sweepAngle.toDouble())) * radius
            paint.shader = LinearGradient(
                startX.toFloat(), startY.toFloat(),
                endX.toFloat(), endY.toFloat(),
                intArrayOf(color.endColor, color.startColor),
                null,
                Shader.TileMode.CLAMP
            )
            if (paint.strokeCap == Paint.Cap.ROUND && point == 1) {
                canvas.drawArc(
                    centerX - radius, centerY - radius,
                    centerX + radius, centerY + radius,
                    startAngle + 2f, sweepAngle - 5f, false, paint
                )
            }
            else {
                canvas.drawArc(
                    centerX - radius, centerY - radius,
                    centerX + radius, centerY + radius,
                    startAngle, sweepAngle, false, paint
                )
            }


            startAngle += sweepAngle
        }
    }

    fun setProgress(progressMap: Map<GradientColor, Float>) {
        if (progressMap.isEmpty()){
            return
        }
        this.progressMap.clear()
        this.progressMap.putAll(progressMap)
        totalProgress = progressMap.values.sum()
        invalidate()
    }
    fun setPoint(input: Int) {
        point = input
        paint.strokeCap = if (totalProgress != 1f) Paint.Cap.ROUND else Paint.Cap.BUTT
    }

}