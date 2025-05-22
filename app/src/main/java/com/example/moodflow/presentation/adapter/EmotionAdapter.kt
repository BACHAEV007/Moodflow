package com.example.moodflow.presentation.adapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.moodflow.R
import com.example.moodflow.databinding.EmotionItemBinding
import kotlinx.parcelize.Parcelize

class EmotionAdapter(
	context: Context,
	private val recyclerView: RecyclerView,
	private val onEmotionClick: (Emotion) -> Unit,
	private val resetDescription: () -> Unit
) :
    RecyclerView.Adapter<EmotionAdapter.EmotionViewHolder>() {
    private val mContext = context
    private var emotionList: MutableList<Emotion> = mutableListOf()
    private var selectedPosition: Int = -1

    fun setEmotions(emotions: List<Emotion>) {
        emotionList = emotions.toMutableList()
        notifyDataSetChanged()
    }

    inner class EmotionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = EmotionItemBinding.bind(view)

        init {
            binding.circleItem.setOnClickListener { v ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    handleClick(position)
                }
            }
        }

        fun bind(emotion: Emotion) {
            binding.circleEmotionText.text = emotion.emotion
            binding.circleItem.setCardBackgroundColor(ContextCompat.getColor(mContext, emotion.color))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.emotion_item, parent, false)
        return EmotionViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmotionViewHolder, position: Int) {
        holder.bind(emotionList[position])
    }

    override fun getItemCount(): Int {
        return emotionList.size
    }

    private fun handleClick(position: Int) {
        if (selectedPosition == position) resetAllAnimations(true)
        else {
            resetAllAnimations()
            selectedPosition = position
            animateItem(position)
            onEmotionClick(emotionList[position])
        }


    }

    private fun animateItem(position: Int) {
        val gridSize = 4
        val selectedRow = position / gridSize
        val selectedCol = position % gridSize

        recyclerView.findViewHolderForAdapterPosition(position)?.let { holder ->
            (holder as? EmotionViewHolder)?.binding?.circleItem?.animate()?.apply {
                scaleX(1.2f)
                scaleY(1.2f)
                duration = 300
                interpolator = AccelerateDecelerateInterpolator()
                start()
            }
        }

        recyclerView.post {
            for (i in 0 until itemCount) {
                if (i != position) {
                    val neighborRow = i / gridSize
                    val neighborCol = i % gridSize

                    val translationX = when {
                        neighborRow == selectedRow && neighborCol > selectedCol -> 40f
                        neighborRow == selectedRow && neighborCol < selectedCol -> -40f
                        else -> 0f
                    }

                    val translationY = when {
                        neighborCol == selectedCol && neighborRow > selectedRow -> 40f
                        neighborCol == selectedCol && neighborRow < selectedRow -> -40f
                        else -> 0f
                    }

                    recyclerView.findViewHolderForAdapterPosition(i)?.let { holder ->
                        (holder as? EmotionViewHolder)?.binding?.circleItem?.animate()?.apply {
                            translationX(translationX)
                            translationY(translationY)
                            duration = 200
                            interpolator = AccelerateDecelerateInterpolator()
                            start()
                        }
                    }
                }
            }
        }
    }

    private fun resetAllAnimations(flag: Boolean = false) {
        if (flag) {
            resetDescription()
        }
        if (selectedPosition == -1) return

        recyclerView.findViewHolderForAdapterPosition(selectedPosition)?.let { holder ->
            (holder as? EmotionViewHolder)?.binding?.circleItem?.animate()?.apply {
                scaleX(1f)
                scaleY(1f)
                duration = 200
                interpolator = AccelerateDecelerateInterpolator()
                start()
            }
        }

        recyclerView.children.forEach { view ->
            view.animate()?.apply {
                translationX(0f)
                translationY(0f)
                duration = 200
                interpolator = AccelerateDecelerateInterpolator()
                start()
            }
        }

        selectedPosition = -1
    }


    init {
        recyclerView.isFocusable = false
        recyclerView.isFocusableInTouchMode = false
        recyclerView.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            false
        }
    }
}

@Parcelize
data class Emotion(
    val emotion: String,
    val color: Int,
    val description: String,
    val iconResName: String
) : Parcelable
