package com.example.moodflow.presentation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.os.Parcelable
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.moodflow.R
import kotlinx.parcelize.Parcelize

class CircleDiagramView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var paint = Paint()
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK

        typeface = ResourcesCompat.getFont(context, R.font.velasans_semi_bold)
        textSize = dpToPx(20f, context)
        textAlign = Paint.Align.CENTER

        letterSpacing = 0f
    }

    private fun dpToPx(dp: Float, context: Context): Float {
        return dp * context.resources.displayMetrics.density
    }

    private var circles = mutableListOf(
        CircleData(15f,
            ContextCompat.getColor(context, R.color.green_end_gradient),
            ContextCompat.getColor(context, R.color.green_start_gradient)
        ),
        CircleData(80f,
            ContextCompat.getColor(context, R.color.yellow_end_gradient),
            ContextCompat.getColor(context, R.color.yellow_start_gradient)
        ),
        CircleData(3f,
            ContextCompat.getColor(context, R.color.red_end_gradient),
            ContextCompat.getColor(context, R.color.red_start_gradient)
        ),
        CircleData(2f,
            ContextCompat.getColor(context, R.color.blue_end_gradient),
            ContextCompat.getColor(context, R.color.blue_start_gradient)
        )
    )

    fun submitList(list: List<CircleData>){
        circles.clear()
        circles.addAll(list)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> widthSize
            else -> context.resources.displayMetrics.widthPixels
        }

        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels

        val aspectRatio = if (screenHeight < 2100) 1f else 1.18f

        val height = (width * aspectRatio).toInt()
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        if (circles.isEmpty()) return

        val circleCount = circles.size
        val maxRadius = minOf(width, height) * 0.4f

        when (circleCount) {
            1 -> drawSingleCircle(canvas, centerX, centerY, circles[0], maxRadius)
            2 -> drawTwoCircles(canvas, circles)
            3 -> drawThreeCircles(canvas, centerY, circles)
            else -> drawFourCircles(canvas, circles)
        }
    }

    private fun drawSingleCircle(canvas: Canvas, centerX: Float, centerY: Float, circle: CircleData, maxRadius: Float) {
        val radius = maxRadius
        val x = centerX
        val y = centerY

        val shader = LinearGradient(
            x, y - radius,
            x, y + radius,
            intArrayOf(circle.startColor, circle.endColor),
            null,
            Shader.TileMode.REPEAT
        )
        paint.shader = shader
        canvas.drawCircle(x, y, radius, paint)

        val text = "${circle.percent.toInt()}%"
        canvas.drawText(text, x, y + (textPaint.textSize / 2), textPaint)
    }

    private fun drawTwoCircles(
        canvas: Canvas,
        circles: List<CircleData>
    ) {
        val canvasArea = canvas.width * canvas.height.toFloat() * 0.7f

        val area1 = (circles[0].percent / 100f) * canvasArea
        val area2 = (circles[1].percent / 100f) * canvasArea

        val radius1 = Math.sqrt((area1 / Math.PI)).toFloat()
        val radius2 = Math.sqrt((area2 / Math.PI)).toFloat()

        val x1 = radius1
        val y1 = radius1

        val x2 = canvas.width - radius2
        val y2 = canvas.height - radius2

        val shader1 = LinearGradient(
            x1, y1 - radius1,
            x1, y1 + radius1,
            intArrayOf(circles[0].startColor, circles[0].endColor),
            null,
            Shader.TileMode.CLAMP
        )
        paint.shader = shader1
        canvas.drawCircle(x1, y1, radius1, paint)
        val text1 = "${circles[0].percent.toInt()}%"
        canvas.drawText(text1, x1, y1 + (textPaint.textSize / 2), textPaint)

        val shader2 = LinearGradient(
            x2, y2 - radius2,
            x2, y2 + radius2,
            intArrayOf(circles[1].startColor, circles[1].endColor),
            null,
            Shader.TileMode.CLAMP
        )
        paint.shader = shader2
        canvas.drawCircle(x2, y2, radius2, paint)
        val text2 = "${circles[1].percent.toInt()}%"
        canvas.drawText(text2, x2, y2 + (textPaint.textSize / 2), textPaint)
    }

    private fun drawThreeCircles(canvas: Canvas, centerY: Float, circles: List<CircleData>) {
        val canvasArea = canvas.width * canvas.height.toFloat() * 0.7f

        val area1 = (circles[0].percent / 100f) * canvasArea
        val area2 = (circles[1].percent / 100f) * canvasArea
        val area3 = (circles[2].percent / 100f) * canvasArea

        val radius1 = Math.sqrt((area1 / Math.PI)).toFloat()
        val radius2 = Math.sqrt((area2 / Math.PI)).toFloat()
        val radius3 = Math.sqrt((area3 / Math.PI)).toFloat()

        val x1 = radius1
        val y1 = radius1
        val shader1 = LinearGradient(
            x1, y1 - radius1,
            x1, y1 + radius1,
            intArrayOf(circles[0].startColor, circles[0].endColor),
            null,
            Shader.TileMode.CLAMP
        )
        paint.shader = shader1
        canvas.drawCircle(x1, y1, radius1, paint)
        val text1 = "${circles[0].percent.toInt()}%"
        canvas.drawText(text1, x1, y1 + (textPaint.textSize / 2), textPaint)

        val x2 = width - radius2
        val y2 = centerY
        val shader2 = LinearGradient(
            x2, y2 - radius2,
            x2, y2 + radius2,
            intArrayOf(circles[1].startColor, circles[1].endColor),
            null,
            Shader.TileMode.CLAMP
        )
        paint.shader = shader2
        canvas.drawCircle(x2, y2, radius2, paint)
        val text2 = "${circles[1].percent.toInt()}%"
        canvas.drawText(text2, x2, y2 + (textPaint.textSize / 2), textPaint)

        val x3 = radius3
        val y3 = height - radius3
        val shader3 = LinearGradient(
            x3, y3 - radius3,
            x3, y3 + radius3,
            intArrayOf(circles[2].startColor, circles[2].endColor),
            null,
            Shader.TileMode.CLAMP
        )
        paint.shader = shader3
        canvas.drawCircle(x3, y3, radius3, paint)
        val text3 = "${circles[2].percent.toInt()}%"
        canvas.drawText(text3, x3, y3 + (textPaint.textSize / 2), textPaint)
    }

    private fun drawFourCircles(canvas: Canvas, circles: List<CircleData>) {
        val canvasArea = canvas.width * canvas.height.toFloat() * 0.7f

        val sortedCircles = circles.sortedByDescending { it.percent }.toMutableList()

        val largest = sortedCircles.removeAt(0)
        val secondLargest = sortedCircles.removeAt(0)
        sortedCircles.add(0, largest)
        sortedCircles.add(3, secondLargest)

        val area1 = (sortedCircles[0].percent / 100f) * canvasArea
        val area2 = (sortedCircles[1].percent / 100f) * canvasArea
        val area3 = (sortedCircles[2].percent / 100f) * canvasArea
        val area4 = (sortedCircles[3].percent / 100f) * canvasArea

        val radius1 = Math.sqrt((area1 / Math.PI)).toFloat()
        val radius2 = Math.sqrt((area2 / Math.PI)).toFloat()
        val radius3 = Math.sqrt((area3 / Math.PI)).toFloat()
        val radius4 = Math.sqrt((area4 / Math.PI)).toFloat()

        val x1 = radius1
        val y1 = radius1
        val shader1 = LinearGradient(
            x1, y1 - radius1,
            x1, y1 + radius1,
            intArrayOf(sortedCircles[0].startColor, sortedCircles[0].endColor),
            null,
            Shader.TileMode.CLAMP
        )
        paint.shader = shader1
        canvas.drawCircle(x1, y1, radius1, paint)
        val text1 = "${sortedCircles[0].percent.toInt()}%"
        canvas.drawText(text1, x1, y1 + (textPaint.textSize / 2), textPaint)

        val x2 = width - radius2
        val y2 = radius2
        val shader2 = LinearGradient(
            x2, y2 - radius2,
            x2, y2 + radius2,
            intArrayOf(sortedCircles[1].startColor,sortedCircles[1].endColor),
            null,
            Shader.TileMode.CLAMP
        )
        paint.shader = shader2
        canvas.drawCircle(x2, y2, radius2, paint)
        val text2 = "${sortedCircles[1].percent.toInt()}%"
        canvas.drawText(text2, x2, y2 + (textPaint.textSize / 2), textPaint)

        val x3 = radius3
        val y3 = height - radius3
        val shader3 = LinearGradient(
            x3, y3 - radius3,
            x3, y3 + radius3,
            intArrayOf(sortedCircles[2].startColor, sortedCircles[2].endColor),
            null,
            Shader.TileMode.CLAMP
        )
        paint.shader = shader3
        canvas.drawCircle(x3, y3, radius3, paint)
        val text3 = "${sortedCircles[2].percent.toInt()}%"
        canvas.drawText(text3, x3, y3 + (textPaint.textSize / 2), textPaint)

        val x4 = width - radius4
        val y4 = height - radius4
        val shader4 = LinearGradient(
            x4, y4 - radius4,
            x4, y4 + radius4,
            intArrayOf(sortedCircles[3].startColor, sortedCircles[3].endColor),
            null,
            Shader.TileMode.CLAMP
        )
        paint.shader = shader4
        canvas.drawCircle(x4, y4, radius4, paint)
        val text4 = "${sortedCircles[3].percent.toInt()}%"
        canvas.drawText(text4, x4, y4 + (textPaint.textSize / 2), textPaint)
    }

    @Parcelize
    data class CircleData(
        val percent: Float,
        val startColor: Int,
        val endColor: Int
    ) : Parcelable
}