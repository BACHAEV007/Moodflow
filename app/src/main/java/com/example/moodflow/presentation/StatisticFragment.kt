package com.example.moodflow.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.moodflow.R
import com.example.moodflow.databinding.StatisticScreenBinding
import com.example.moodflow.presentation.adapter.StatisticAdapter
import com.example.moodflow.presentation.viewmodel.StatisticViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StatisticFragment : Fragment(R.layout.statistic_screen) {
    private val viewModel: StatisticViewModel by sharedViewModel()
    private lateinit var adapter: StatisticAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var binding: StatisticScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = StatisticScreenBinding.bind(view)
        adapter = StatisticAdapter(this, viewModel)
        viewPager = binding.pager
        viewPager.adapter = adapter

        tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = viewModel.weekLabels[position]
        }.attach()
        binding.pager.setCurrentItem(viewModel.currentWeek.value, false)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(pos: Int) {
                viewModel.selectWeek(pos)
            }
        })

    }
    override fun onStart() {
        super.onStart()
        viewModel.invalidateCache()
    }

}






