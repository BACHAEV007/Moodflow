package com.example.moodflow.presentation.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moodflow.presentation.VerticalStatisticScreenFragment
import com.example.moodflow.presentation.viewmodel.StatisticViewModel
import com.example.moodflow.utils.Constants.WEEK_INDEX

class StatisticAdapter(fragment: Fragment, private val viewModel: StatisticViewModel) : FragmentStateAdapter(fragment) {
    private val fragments = mutableMapOf<Int, Fragment>()

    override fun getItemCount(): Int = viewModel.weekRanges.size

    override fun createFragment(position: Int): Fragment {
        val fragment = VerticalStatisticScreenFragment().apply {
            arguments = bundleOf(WEEK_INDEX to position)
        }
        fragments[position] = fragment
        return fragment
    }

    fun getFragmentAt(position: Int): Fragment? = fragments[position]
}