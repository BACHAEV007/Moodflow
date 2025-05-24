package com.example.moodflow.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moodflow.R
import com.example.moodflow.databinding.AddNoteScreenBinding
import com.example.moodflow.presentation.mapper.toCardStyle
import com.example.moodflow.presentation.viewmodel.AddEmotionViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AddNotionFragment : Fragment(R.layout.add_note_screen) {

	private lateinit var binding: AddNoteScreenBinding
	private val viewModel: AddEmotionViewModel by sharedViewModel()
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding = AddNoteScreenBinding.bind(view)
		initAllChips()
		binding.backButton.setOnClickListener {
			findNavController().popBackStack()
		}
		setFragmentResultListener("requestKey") { requestKey, bundle ->
			val result = bundle.getLong("bundleKey")
			viewModel.getEmotionById(result)
		}
		lifecycleScope.launch {
			var lastId: Long? = null
			viewModel.state.collect { content ->
				content.let { c ->
					if (c.id != lastId && c.id != null) {
						initAllChips()
						syncChipsFromState(binding.activitiesChip,    c.activities,   requireContext())
						syncChipsFromState(binding.environmentChip,   c.companions,   requireContext())
						syncChipsFromState(binding.placeChip,         c.locations,    requireContext())
						lastId = c.id
					}
					val style = c.emotionType.toCardStyle(requireContext(), c.iconResName)

					with(binding) {
						cardDate.text = c.timestamp
						cardEmotionDesc.text = c.emotion
						card.background =
							ContextCompat.getDrawable(requireContext(), style.backgroundDrawable)
						cardEmotionDesc.setTextColor(style.textColor)
						feelIcon.setImageResource(style.iconRes)
					}
				}
			}
		}
		binding.addEmotionNotionButton.setOnClickListener {
			viewModel.addEmotion()
			findNavController().popBackStack(R.id.journalFragment, true)
			findNavController().navigate(R.id.journalFragment)
		}
	}

	private fun initAllChips() {
		val groups = listOf(
			Triple(binding.addActivityChip,   binding.editActivityChip,   binding.activitiesChip)   to { sel: List<String> -> viewModel.setActivities(sel) },
			Triple(binding.addEnvironmentChip,binding.editEnvironmentChip,binding.environmentChip) to { sel: List<String> -> viewModel.setCompanions(sel) },
			Triple(binding.addPlaceChip,      binding.editPlaceChip,      binding.placeChip)       to { sel: List<String> -> viewModel.setLocations(sel) }
		)

		groups.forEachIndexed { index, (triple, onSelected) ->
			val pos = index + 1
			val (addChip, editText, chipGroup) = triple

			chipGroup.removeAllViews()

			setupChip(fillChipData(pos, requireContext()), chipGroup)

			chipGroup.addView(editText)
			chipGroup.addView(addChip)

			addChip.setOnClickListener {
				toggleEditTextVisibility(editText, addChip)
				showKeyboard(editText)
			}
			editText.setOnFocusChangeListener { _, hasFocus ->
				if (!hasFocus) hideEditText(editText, addChip)
			}
			editText.setOnEditorActionListener { v, actionId, _ ->
				if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
					val newText = v.text.toString().trim()
					if (newText.isNotEmpty()) {
						addNewChip(newText, chipGroup, requireContext())
					}
					hideKeyboard(v)
					hideEditText(editText, addChip)
					true
				} else {
					false
				}
			}

			chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
				val selected = checkedIds.mapNotNull { id ->
					(group.findViewById<Chip>(id)).text.toString().takeIf(String::isNotBlank)
				}
				onSelected(selected)
			}
		}
	}

	private fun syncChipsFromState(
		chipGroup: ChipGroup,
		values: List<String>?,
		context: Context
	) {
		values?.forEach { text ->
			val existing = chipGroup.children
				.filterIsInstance<Chip>()
				.find { it.text.toString() == text }

			if (existing != null) {
				existing.isChecked = true
			} else {
				val chip = LayoutInflater.from(context)
					.inflate(R.layout.chip_item, chipGroup, false) as Chip
				chip.text = text
				chip.isCheckable = true
				chip.isChecked = true
				chip.chipBackgroundColor =  ContextCompat.getColorStateList(context, R.color.gray_button)
				chip.setOnCheckedChangeListener { _, isChecked ->
					chip.chipBackgroundColor = ContextCompat.getColorStateList(
						context,
						if (isChecked) R.color.gray_button else R.color.gray
					)
				}
				chip.setOnCloseIconClickListener { chipGroup.removeView(it) }
				chipGroup.addView(chip, chipGroup.childCount - 2)
			}
		}
	}

	private fun hideKeyboard(view: View) {
		val imm =
			requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		imm.hideSoftInputFromWindow(view.windowToken, 0)
	}

	private fun showKeyboard(view: View) {
		val imm =
			requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
	}

	private fun toggleEditTextVisibility(editText: EditText, addChip: Chip) {
		addChip.visibility = View.GONE
		editText.visibility = View.VISIBLE
		editText.requestFocus()
	}

	private fun addNewChip(text: String, chipGroup: ChipGroup, context: Context) {
		val chip =
			LayoutInflater.from(context).inflate(R.layout.chip_item, chipGroup, false) as Chip
		chip.text = text
		chip.setOnCheckedChangeListener { _, isChecked ->
			chip.chipBackgroundColor = ContextCompat.getColorStateList(
				requireContext(),
				if (isChecked) R.color.gray_button else R.color.gray
			)
		}
		chip.setOnCloseIconClickListener { chipGroup.removeView(chip) }
		chipGroup.addView(chip, chipGroup.childCount - 2)
	}

	private fun hideEditText(editText: EditText, addChip: Chip) {
		editText.text.clear()
		editText.visibility = View.GONE
		addChip.visibility = View.VISIBLE
	}

	private fun fillChipData(position: Int, context: Context): MutableList<String> =
		when (position) {
			1    -> mutableListOf(
				context.getString(R.string.chip_food),
				context.getString(R.string.chip_meeting_friends),
				context.getString(R.string.chip_workout),
				context.getString(R.string.chip_hobby),
				context.getString(R.string.chip_rest),
				context.getString(R.string.chip_trip)
			)

			2    -> mutableListOf(
				context.getString(R.string.chip_alone),
				context.getString(R.string.chip_friends),
				context.getString(R.string.chip_family),
				context.getString(R.string.chip_colleagues),
				context.getString(R.string.chip_partner),
				context.getString(R.string.chip_pets)
			)

			3    -> mutableListOf(
				context.getString(R.string.chip_home),
				context.getString(R.string.chip_work),
				context.getString(R.string.chip_school),
				context.getString(R.string.chip_transport),
				context.getString(R.string.chip_street)
			)

			else -> mutableListOf(context.getString(R.string.chip_empty))
		}

	private fun setupChip(list: MutableList<String>, view: ChipGroup) {
		list.forEach { text ->
			val chip = layoutInflater.inflate(R.layout.chip_item, view, false) as Chip
			chip.text = text
			chip.setOnCheckedChangeListener { _, isChecked ->
				chip.chipBackgroundColor = ContextCompat.getColorStateList(
					requireContext(),
					if (isChecked) R.color.gray_button else R.color.gray
				)
			}
			view.addView(chip, view.size - 2)
		}
		view.chipSpacingHorizontal = resources.getDimensionPixelSize(R.dimen.chip_spacing)
		view.chipSpacingVertical = resources.getDimensionPixelSize(R.dimen.chip_spacing)
	}


}