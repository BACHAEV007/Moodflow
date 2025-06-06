package com.example.moodflow.presentation.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position < state.itemCount - 1) {
            outRect.bottom = verticalSpaceHeight
        }
        if (position == 0) {
            outRect.top = verticalSpaceHeight
        }
    }
}