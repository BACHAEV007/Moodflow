package com.example.moodflow

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.moodflow.databinding.VerticalStatisticScreenBinding

class VerticalStatisticScreenFragment : Fragment() {
    private lateinit var binding: VerticalStatisticScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.vertical_statistic_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = VerticalStatisticScreenBinding.bind(view)

        val verticalPager = binding.verticalPager
        verticalPager.adapter = VerticalStatisticScreenAdapter(this)
        verticalPager.offscreenPageLimit = 2

        verticalPager.clipToPadding = false
        verticalPager.clipChildren = false
        val paddingBottom = 104
        verticalPager.setPadding(0, 0, 0, paddingBottom)

        verticalPager.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.parent.requestDisallowInterceptTouchEvent(true)
                    false
                }
                MotionEvent.ACTION_MOVE -> {
                    v.parent.requestDisallowInterceptTouchEvent(true)
                    false
                }
                else -> false
            }
        }
        val indicator = binding.indicator
        verticalPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicator.setSelectedPosition(position)
                if (position == verticalPager.adapter?.itemCount?.minus(1)) {
                    verticalPager.setPadding(0, 0, 0, 0)
                } else {
                    verticalPager.setPadding(0, 0, 0, paddingBottom)
                }
            }
        })
    }
}

class VerticalStatisticScreenAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EmotionCategoriesFragment()
            1 -> WeekEmotionFragment()
            2 -> FrequentScreenFragment()
            3 -> DayEmotionFragment()
            else -> throw IllegalStateException("Недопустимая позиция")
        }
    }
}
