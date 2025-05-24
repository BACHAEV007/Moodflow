package com.example.moodflow.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.moodflow.R
import com.example.moodflow.databinding.VerticalStatisticScreenBinding
import com.example.moodflow.presentation.viewmodel.StatisticViewModel
import com.example.moodflow.utils.Constants.WEEK_INDEX
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class VerticalStatisticScreenFragment : Fragment() {
    private lateinit var binding: VerticalStatisticScreenBinding
    private lateinit var adapter: VerticalStatisticScreenAdapter
    private val weekIndex: Int by lazy {
        requireArguments().getInt(WEEK_INDEX)
    }
    private val viewModel: StatisticViewModel by sharedViewModel()

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

        viewModel.selectWeek(weekIndex)
        lifecycleScope.launch {
            viewModel.getWeekEmotionsFlow(weekIndex)
                .collect { emotions ->
                    adapter = VerticalStatisticScreenAdapter(this@VerticalStatisticScreenFragment, weekIndex, viewModel)
                    binding.verticalPager.adapter = adapter
                    binding.verticalPager.offscreenPageLimit = 2
                    binding.verticalPager.clipToPadding = false
                    binding.verticalPager.clipChildren = false
                }
        }



        val verticalPager = binding.verticalPager
        verticalPager.offscreenPageLimit = 2

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

class VerticalStatisticScreenAdapter(
    fragment: Fragment,
    private val weekIndex: Int,
    private val viewModel: StatisticViewModel
) : FragmentStateAdapter(fragment) {
    private val fragments = mutableMapOf<Int, Fragment>()
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val circles = viewModel.getCircleData(weekIndex)
                EmotionCategoriesFragment.newInstance(ArrayList(circles.first), circles.second)
            }
            1 -> {
                val weeksData = viewModel.getWeekEmotions(weekIndex)
                WeekEmotionFragment.newInstance(ArrayList(weeksData))
            }
            2 -> {
                val frequentData = viewModel.getFrequentEmotionData(weekIndex)
                FrequentScreenFragment.newInstance(ArrayList(frequentData))
            }
            3 -> DayEmotionFragment().apply {
                arguments = bundleOf("arg_week_index" to weekIndex)
            }
            else -> throw IllegalStateException("")
        }
    }

    fun getFragmentAt(position: Int): Fragment? = fragments[position]
}
