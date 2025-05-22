package com.example.moodflow.presentation.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.moodflow.R

class CustomClockView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private var hours: String = "00"
    private var minutes: String = "00"

    private val textPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
        textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            48f,
            Resources.getSystem().displayMetrics
        )

        typeface = ResourcesCompat.getFont(context, R.font.velasans_semi_bold)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val paddingDp = 24f
        val paddingPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            paddingDp,
            resources.displayMetrics
        )
        val radius = 8f
        val radiusDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            radius,
            resources.displayMetrics
        )

        paint.color = Color.WHITE
        val leftRect = RectF(0f, 0f, width / 2f - paddingPx / 2, height)
        canvas.drawRoundRect(leftRect, radiusDp, radiusDp, paint)

        val rightRect = RectF(width / 2f + paddingPx / 2, 0f, width, height)
        canvas.drawRoundRect(rightRect, radiusDp, radiusDp, paint)

        val leftTextX = leftRect.centerX()
        val leftTextY = leftRect.centerY() - (textPaint.descent() + textPaint.ascent()) / 2
        canvas.drawText(hours, leftTextX, leftTextY, textPaint)

        val rightTextX = rightRect.centerX()
        val rightTextY = rightRect.centerY() - (textPaint.descent() + textPaint.ascent()) / 2
        canvas.drawText(minutes, rightTextX, rightTextY, textPaint)
    }

    fun setTime(hours: Int, minutes: Int) {
        this.hours = String.format("%02d", hours)
        this.minutes = String.format("%02d", minutes)
        invalidate()
    }

    fun getTimeString() : String{
        return "${hours}:${minutes}"
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val desiredHeight = (desiredWidth / 5).coerceAtMost(MeasureSpec.getSize(heightMeasureSpec))
        setMeasuredDimension(desiredWidth, desiredHeight)
    }
}