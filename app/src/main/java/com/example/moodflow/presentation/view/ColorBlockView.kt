package com.example.moodflow.presentation.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.moodflow.R

class ColorBlocksView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val blocks = mutableListOf<Block>()
    private val rectF = RectF()
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var cornerRadius: Float = 8f
    init {

        cornerRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            8f,
            Resources.getSystem().displayMetrics
        )

        linePaint.color = Color.BLACK
        linePaint.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            4f,
            Resources.getSystem().displayMetrics
        ) 

        textPaint.color = Color.BLACK
        textPaint.textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            12f,
            Resources.getSystem().displayMetrics
        )
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.typeface = ResourcesCompat.getFont(context, R.font.velasans_bold)
    }

    data class Block(val color: Int = R.color.bottom_nav_color, var percentage: Float = 0F, val startColor: Int = 1, val endColor: Int = 1)

    fun setBlocks(newBlocks: List<Block> = listOf(Block())) {
        blocks.clear()
        blocks.addAll(newBlocks)
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
        if (blocks.isEmpty()) return
        var currentTop = 0f
        for ((index, block) in blocks.withIndex()) {
            val blockHeight =
                if (block.percentage == 0f) height.toFloat() else height * block.percentage

            if (block.percentage == 0f) {
                paint.color = getColorFromResource(block.color)
                paint.shader = null
                rectF.set(0f, currentTop, width.toFloat(), currentTop + blockHeight)
                canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
            } else {
                paint.shader = LinearGradient(
                    0f, currentTop,
                    width.toFloat(), currentTop + blockHeight,
                    intArrayOf(
                        getColorFromResource(block.startColor),
                        getColorFromResource(block.endColor)
                    ),
                    null,
                    Shader.TileMode.CLAMP
                )

                rectF.set(0f, currentTop, width.toFloat(), currentTop + blockHeight)
                canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)

                val text = "${(block.percentage * 100).toInt()}%"
                val textX = width.toFloat() / 2
                val textY =
                    currentTop + (blockHeight / 2) - (textPaint.descent() + textPaint.ascent()) / 2
                canvas.drawText(text, textX, textY, textPaint)
            }

            if (index < blocks.size - 1) {
                val lineY = currentTop + blockHeight
                canvas.drawLine(
                    0f, lineY,
                    width.toFloat(), lineY,
                    linePaint
                )
            }

            currentTop += blockHeight
        }
        paint.shader = null
    }
}
