package com.example.moodflow.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moodflow.VerticalStatisticScreenFragment

class StatisticAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {
        val fragment = VerticalStatisticScreenFragment()
        return fragment
    }
}