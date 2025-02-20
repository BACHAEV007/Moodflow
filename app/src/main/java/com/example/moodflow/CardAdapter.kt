package com.example.moodflow

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moodflow.databinding.CardItemBinding
import com.example.moodflow.uicontent.CardColor
import com.example.moodflow.uicontent.CardContent
import com.example.moodflow.uicontent.CardStyle

class CardAdapter() : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    val cardList = listOf(
        CardContent(data = "вчера, 23:40", color = CardColor.GREEN, feeling = "спокойствие"),
        CardContent(data = "вчера, 23:40", color = CardColor.BLUE, feeling = "выгорание"),
        CardContent(data = "воскресенье, 23:40", color = CardColor.YELLOW, feeling = "продуктивность"),
        CardContent(data = "воскресенье, 23:40", color = CardColor.RED, feeling = "беспокойство")
    )
    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = CardItemBinding.bind(view)

        fun bind(card: CardContent)= with(binding){
            textView10.text = card.feeling
            textView9.text = card.data
            val style = getCardStyle(root.context, card.color)

            root.background = GradientDrawable(
                GradientDrawable.Orientation.TL_BR, style.gradientColors
            ).apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 16.dpToPx().toFloat()
                gradientType = GradientDrawable.RADIAL_GRADIENT
                setGradientCenter(1f, 0.1f)
                gradientRadius = 400.dpToPx().toFloat()
            }
            root.background.alpha = (0.4 * 255).toInt()

            textView10.setTextColor(style.textColor)
            feelIcon.setImageResource(style.iconRes)
        }

    }

    private fun getCardStyle(context: Context, color: CardColor): CardStyle {
        return when (color) {
            CardColor.BLUE -> CardStyle(
                gradientColors = intArrayOf(Color.parseColor("#00AAFF"), Color.parseColor("#002C2E")),
                textColor = ContextCompat.getColor(context, R.color.blue_card_text),
                iconRes = R.drawable.blue_image_card
            )
            CardColor.GREEN -> CardStyle(
                gradientColors = intArrayOf(Color.parseColor("#00FF55"), Color.parseColor("#002C2E")),
                textColor = ContextCompat.getColor(context, R.color.green_card_text),
                iconRes = R.drawable.green_image_card
            )
            CardColor.YELLOW -> CardStyle(
                gradientColors = intArrayOf(Color.parseColor("#FFAA00"), Color.parseColor("#002C2E")),
                textColor = ContextCompat.getColor(context, R.color.yellow_card_text),
                iconRes = R.drawable.yellow_image_card
            )
            CardColor.RED -> CardStyle(
                gradientColors = intArrayOf(Color.parseColor("#FF0000"), Color.parseColor("#002C2E")),
                textColor = ContextCompat.getColor(context, R.color.red_card_text),
                iconRes = R.drawable.red_image_card
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cardList[position])
    }

    override fun getItemCount(): Int {
        return cardList.size
    }
}

class SpaceItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.bottom = spaceHeight
    }
}

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()