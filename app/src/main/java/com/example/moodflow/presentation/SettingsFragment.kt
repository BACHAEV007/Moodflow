package com.example.moodflow.presentation

import android.Manifest
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moodflow.R
import com.example.moodflow.databinding.SettingsScreenBinding
import com.example.moodflow.presentation.adapter.SettingsAdapter
import com.example.moodflow.presentation.decorations.VerticalSpaceItemDecoration
import com.example.moodflow.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class SettingsFragment : Fragment(R.layout.settings_screen) {

	private lateinit var binding: SettingsScreenBinding
	private lateinit var settingsAdapter: SettingsAdapter
	private lateinit var timePicker: TimePicker
	private val viewModel: SettingsViewModel by viewModel()
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding = SettingsScreenBinding.bind(view)
		settingsAdapter = SettingsAdapter { position ->
			viewModel.removeNotification(position)
		}
		lifecycleScope.launch {
			viewModel.state.collect { state ->

				binding.name.text = (state.user?.name ?: R.string.mock_name).toString()
				val avatarUrl = state.user?.avatar
				Glide.with(this@SettingsFragment)
					.load(avatarUrl)
					.placeholder(R.drawable.avatar_example)
					.error(R.drawable.avatar_example)
					.circleCrop()
					.into(binding.avatar)
				settingsAdapter.submitList(state.notifications)
			}
		}
		timePicker = binding.timePicker

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
		lifecycleScope.launch {
			viewModel.notificationsEnabled.collect { enabled ->
				switch.isChecked = enabled
				val color = if (enabled) R.color.green_checkbox else android.R.color.white
				switch.trackTintList =
					ColorStateList.valueOf(ContextCompat.getColor(requireContext(), color))
			}
		}
		switch.setOnCheckedChangeListener { _, isChecked ->
			if (isChecked) {
				val hasPermission = checkAndRequestNotificationPermission()
				if (!hasPermission) {
					switch.isChecked = false
					return@setOnCheckedChangeListener
				}
				switch.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.green_checkbox))
			} else {
				switch.trackTintList = ColorStateList.valueOf(Color.WHITE)
			}
			viewModel.toggleNotifications(isChecked)
		}
		lifecycleScope.launch {
			viewModel.fingerprintEnabled.collect { enabled ->
				fingerSwitch.isChecked = enabled
				val color = if (enabled) R.color.green_checkbox else android.R.color.white
				fingerSwitch.trackTintList =
					ColorStateList.valueOf(ContextCompat.getColor(requireContext(), color))
			}
		}
		fingerSwitch.setOnCheckedChangeListener { _, isChecked ->
			viewModel.toggleFingerprint(isChecked)
		}

		parentFragmentManager.setFragmentResultListener("clock_clicked", viewLifecycleOwner) { _, _ ->
			showTimePicker()
		}

		parentFragmentManager.setFragmentResultListener("save_button_clicked", viewLifecycleOwner) { _, bundle ->
			val time = bundle.getString("time")
			time?.let {
				viewModel.addNotification(it)
			}
		}
	}

	private fun checkAndRequestNotificationPermission(): Boolean {
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
				!= PackageManager.PERMISSION_GRANTED
			) {
				requestPermissions(
					arrayOf(Manifest.permission.POST_NOTIFICATIONS),
					REQUEST_CODE_POST_NOTIFICATIONS
				)
				false
			} else {
				true
			}
		} else {
			true
		}
	}

	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray
	) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
			if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
				binding.toggleSwitch.isChecked = true
				binding.toggleSwitch.trackTintList =
					ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.green_checkbox))
			} else {
				Toast.makeText(requireContext(), getString(R.string.notification_permission), Toast.LENGTH_LONG).show()
				binding.toggleSwitch.isChecked = false
			}
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

	companion object {

		private const val REQUEST_CODE_POST_NOTIFICATIONS = 1001
	}

}