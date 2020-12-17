package com.example.meditation.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meditation.MyApplication
import com.example.meditation.R
import com.example.meditation.viewmodel.MainViewModel

class ThemeSelectedDialog: DialogFragment() {

    val appContext = MyApplication.appContext
    private val themeList = MyApplication.themeList
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val recyclerView = RecyclerView(requireContext())
        with(recyclerView) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = ThemeSelectAdapter(themeList, viewModel)
        }
        val dialog = AlertDialog.Builder(activity).apply {
            setTitle(R.string.select_theme)
            setView(recyclerView)
        }.create()

        viewModel.txtTheme.observe(requireActivity(), Observer {
            dialog.dismiss()
        })

        return dialog
    }
}