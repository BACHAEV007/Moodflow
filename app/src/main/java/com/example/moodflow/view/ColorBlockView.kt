package com.example.moodflow.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class ColorBlocksView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val blocks = mutableListOf<Block>()
    private val rectF = RectF()
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
    }

    data class Block(val color: Int = Color.parseColor("#333333"), val percentage: Float = 1F)

    fun setBlocks(newBlocks: List<Block> = listOf(Block(Color.parseColor("#333333")))) {
        blocks.clear()
        blocks.addAll(newBlocks)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (blocks.isEmpty()) return

        var currentTop = 0f

        for (block in blocks) {
            val blockHeight = height * block.percentage
            paint.color = block.color

            var currentTop = 0f
            for ((index, block) in blocks.withIndex()) {
                val blockHeight = height * block.percentage
                paint.color = block.color

                rectF.set(0f, currentTop, width.toFloat(), currentTop + blockHeight)
                canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)

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
        }
    }
}