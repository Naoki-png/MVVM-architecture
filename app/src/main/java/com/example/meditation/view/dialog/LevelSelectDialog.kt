package com.example.meditation.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.meditation.R
import com.example.meditation.viewmodel.MainViewModel

class LevelSelectDialog: DialogFragment() {

    var selectedItemId = 0

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(activity).apply {
            setTitle(R.string.select_level)
            setSingleChoiceItems(R.array.level_list, selectedItemId) { dialog, which ->
                selectedItemId = which
                viewModel.setLevel(selectedItemId)
                dialog.dismiss()
            }
        }.create()
        return dialog
    }
}