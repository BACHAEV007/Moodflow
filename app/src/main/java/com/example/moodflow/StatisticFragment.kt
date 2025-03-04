package com.example.moodflow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.moodflow.adapter.StatisticAdapter
import com.example.moodflow.databinding.StatisticScreenBinding
import com.example.moodflow.view.CircleDiagramView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class StatisticFragment : Fragment(R.layout.statistic_screen) {

    private lateinit var adapter: StatisticAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var binding: StatisticScreenBinding
    private val tabNames: Array<String> = arrayOf(
        "17-23 фев",
        "10-16 фев",
        "3-9 фев",
        "27 янв-2 фев",
        "26 янв-19 янв",
        "18 янв-25 янв",
        "10 янв-17 янв",
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = StatisticScreenBinding.bind(view)
        adapter = StatisticAdapter(this)
        viewPager = binding.pager
        viewPager.adapter = adapter

        tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()

    }


}






