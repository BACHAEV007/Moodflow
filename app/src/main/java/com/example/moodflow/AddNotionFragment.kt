package com.example.moodflow

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moodflow.databinding.AddNoteScreenBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.internal.ViewUtils.hideKeyboard

class AddNotionFragment : Fragment(R.layout.add_note_screen) {
    private lateinit var binding: AddNoteScreenBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AddNoteScreenBinding.bind(view)
        binding.apply {
            setupChip(fillChipData(1), activitiesChip)
            setupChip(fillChipData(2), environmentChip)
            setupChip(fillChipData(3), placeChip)
        }
        setupChipListeners()
        binding.backButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }
    private fun setupChipListeners() {
        listOf(
            Triple(binding.addActivityChip, binding.editActivityChip, binding.activitiesChip),
            Triple(binding.addEnvironmentChip, binding.editEnvironmentChip, binding.environmentChip),
            Triple(binding.addPlaceChip, binding.editPlaceChip, binding.placeChip)
        ).forEach { (addChip, editText, chipGroup) ->

            addChip.setOnClickListener {
                toggleEditTextVisibility(editText, addChip)
                showKeyboard(editText)
            }

            editText.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    hideEditText(editText, addChip)
                }
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
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    private fun showKeyboard(view: View) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
    private fun toggleEditTextVisibility(editText: EditText, addChip: Chip) {
        addChip.visibility = View.GONE
        editText.visibility = View.VISIBLE
        editText.requestFocus()
    }
    private fun addNewChip(text: String, chipGroup: ChipGroup, context: Context) {
        val chip = LayoutInflater.from(context).inflate(R.layout.chip_item, chipGroup, false) as Chip
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

    private fun fillChipData(position: Int): MutableList<String> =
        when (position) {
            1 -> mutableListOf(
                "Приём пищи", "Встреча с друзьями", "Тренировка", "Хобби", "Отдых", "Поездка")
            2 -> mutableListOf(
                "Один", "Друзья", "Семья", "Коллеги", "Партнёр", "Питомцы")
            3 -> mutableListOf(
                "Дом", "Работа", "Школа", "Транспорт", "Улица")
            else -> mutableListOf("")
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