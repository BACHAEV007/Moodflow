package com.example.moodflow

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moodflow.databinding.BottomSheetBinding
import com.example.moodflow.view.CustomClockView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class NotionBottomSheetFragment : BottomSheetDialogFragment(){
    private lateinit var binding: BottomSheetBinding
    private lateinit var customClockView: CustomClockView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetBinding.bind(inflater.inflate(R.layout.bottom_sheet, container))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customClockView = binding.clock

        parentFragmentManager.setFragmentResultListener("time_selected", viewLifecycleOwner) { _, bundle ->
            val hour = bundle.getInt("hour", 0)
            val minute = bundle.getInt("minute", 0)
            customClockView.setTime(hour, minute)
        }

        binding.clock.setOnClickListener {
            parentFragmentManager.setFragmentResult("clock_clicked", Bundle())
        }

        binding.saveButton.setOnClickListener {
            parentFragmentManager.setFragmentResult("save_button_clicked", Bundle().apply { putString("time", customClockView.getTimeString()) })
            dismiss()
        }
    }



    override fun getTheme() = R.style.AppBottomSheetDialogTheme
}