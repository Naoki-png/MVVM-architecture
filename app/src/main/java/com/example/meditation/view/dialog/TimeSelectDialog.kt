package com.example.meditation.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.meditation.R
import com.example.meditation.viewmodel.MainViewModel

class TimeSelectDialog: DialogFragment() {
    private var selectedItemId = 0

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(activity).apply {
            setTitle(R.string.select_theme)
            setSingleChoiceItems(R.array.time_list, selectedItemId) { dialog, which ->
                selectedItemId = which
                viewModel.setTime(selectedItemId)
                dialog.dismiss()
            }
        }.create()
        return dialog
    }
}