package com.example.moodflow

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.moodflow.databinding.VerticalStatisticScreenBinding

class VerticalStatisticScreenFragment : Fragment() {
    private lateinit var binding: VerticalStatisticScreenBinding
    private lateinit var adapter: VerticalStatisticScreenAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.vertical_statistic_screen, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = VerticalStatisticScreenBinding.bind(view)

        val verticalPager = binding.verticalPager
        adapter = VerticalStatisticScreenAdapter(this)
        verticalPager.offscreenPageLimit = 2
        verticalPager.adapter = adapter
        verticalPager.clipToPadding = false
        verticalPager.clipChildren = false
        val displayMetrics = DisplayMetrics()
        val windowManager = requireActivity().windowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val screenHeight = displayMetrics.heightPixels
        val paddingBottom = if (screenHeight < 1700) {
            0
        } else if (screenHeight < 2100) {
            96
        } else {
            134
        }
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
    private val fragments = mutableMapOf<Int, Fragment>()
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EmotionCategoriesFragment()
            1 -> WeekEmotionFragment()
            2 -> FrequentScreenFragment()
            3 -> DayEmotionFragment()
            else -> throw IllegalStateException("")
        }
    }

    fun getFragmentAt(position: Int): Fragment? = fragments[position]
}
