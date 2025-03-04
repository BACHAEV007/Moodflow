package com.example.moodflow

import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TimePicker
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodflow.adapter.SettingsAdapter
import com.example.moodflow.databinding.SettingsScreenBinding
import com.example.moodflow.decorations.VerticalSpaceItemDecoration
import java.util.Calendar


class SettingsFragment : Fragment(R.layout.settings_screen) {
    private lateinit var binding: SettingsScreenBinding
    private lateinit var settingsAdapter: SettingsAdapter
    private lateinit var timePicker: TimePicker
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SettingsScreenBinding.bind(view)
        settingsAdapter = SettingsAdapter { position ->
            settingsAdapter.removeItem(position)
        }
        timePicker = binding.timePicker
        val timeList = arguments?.getStringArrayList("time_key") ?: emptyList()
        settingsAdapter.submitList(timeList)
        val switch = binding.toggleSwitch
        val fingerSwitch = binding.toggleSwitchFinger
        binding.notionRecycler.apply {
            adapter = settingsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val spaceHeight = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                16f,
                resources.displayMetrics
            ).toInt()
            addItemDecoration(VerticalSpaceItemDecoration(spaceHeight))
        }
        binding.addNotionButton.setOnClickListener {
            showNotionBottomSheet()
        }
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switch.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green_checkbox))
            } else {
                switch.trackTintList = ColorStateList.valueOf(Color.WHITE)
            }
        }

        fingerSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                fingerSwitch.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green_checkbox))
            } else {
                fingerSwitch.trackTintList = ColorStateList.valueOf(Color.WHITE)
            }
        }

        parentFragmentManager.setFragmentResultListener("clock_clicked", viewLifecycleOwner) { _, _ ->
            showTimePicker()
        }

        parentFragmentManager.setFragmentResultListener("save_button_clicked", viewLifecycleOwner) { _, bundle ->
            val time = bundle.getString("time")
            time?.let { settingsAdapter.addNotification(it) }
        }
    }

    private fun showNotionBottomSheet() {
        val bottomSheet = NotionBottomSheetFragment()
        bottomSheet.show(parentFragmentManager, "tag")
    }

    private fun showTimePicker() {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val currentMinute = Calendar.getInstance().get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            R.style.CustomTimePickerDialogTheme,
            { _, hourOfDay, minute ->
                val result = Bundle().apply {
                    putInt("hour", hourOfDay)
                    putInt("minute", minute)
                }
                parentFragmentManager.setFragmentResult("time_selected", result)
            },
            currentHour,
            currentMinute,
            true
        )
        timePickerDialog.show()
    }

}