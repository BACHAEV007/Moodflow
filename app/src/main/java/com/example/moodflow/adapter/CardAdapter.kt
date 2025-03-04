package com.example.moodflow.adapter

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moodflow.R
import com.example.moodflow.databinding.CardItemBinding
import com.example.moodflow.uicontent.CardColor
import com.example.moodflow.uicontent.CardContent
import com.example.moodflow.uicontent.CardStyle

class CardAdapter() : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    private val cardList = mutableListOf<CardContent>()

    fun submitList(newCards: List<CardContent>) {
        cardList.clear()
        cardList.addAll(newCards)
        notifyDataSetChanged()
    }

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = CardItemBinding.bind(view)

        fun bind(card: CardContent)= with(binding){
            cardEmotionDesc.text = card.feeling
            cardData.text = card.data
            val style = getCardStyle(root.context, card.color)

            root.background = ContextCompat.getDrawable(root.context, style.backgroundDrawable)

            cardEmotionDesc.setTextColor(style.textColor)
            feelIcon.setImageResource(style.iconRes)
        }

    }

    private fun getCardStyle(context: Context, color: CardColor): CardStyle {
        return when (color) {
            CardColor.BLUE -> CardStyle(
                backgroundDrawable = R.drawable.blue_card_shape,
                textColor = ContextCompat.getColor(context, R.color.blue_card_text),
                iconRes = R.drawable.blue_image_card
            )
            CardColor.GREEN -> CardStyle(
                backgroundDrawable = R.drawable.green_card_shape,
                textColor = ContextCompat.getColor(context, R.color.green_card_text),
                iconRes = R.drawable.green_image_card
            )
            CardColor.YELLOW -> CardStyle(
                backgroundDrawable = R.drawable.yellow_card_shape,
                textColor = ContextCompat.getColor(context, R.color.yellow_card_text),
                iconRes = R.drawable.yellow_image_card
            )
            CardColor.RED -> CardStyle(
                backgroundDrawable = R.drawable.red_card_shape,
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