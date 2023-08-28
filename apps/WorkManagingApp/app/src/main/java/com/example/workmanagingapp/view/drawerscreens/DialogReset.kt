package com.example.workmanagingapp.view.drawerscreens

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.workmanagingapp.R
import com.example.workmanagingapp.viewmodel.MyViewModel

class DialogReset(
    private val viewModel: MyViewModel
): DialogFragment() {
    private lateinit var btnCancel: Button
    private lateinit var btnConfirm: Button
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflater = requireActivity().layoutInflater
        val view = layoutInflater.inflate(R.layout.dialog_reset, null, false)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)

        btnCancel = view.findViewById(R.id.btnResetCancel)
        btnCancel.setOnClickListener {
            dismiss()
        }
        btnConfirm = view.findViewById(R.id.btnResetConfirm)
        btnConfirm.setOnClickListener {
            viewModel.resetWorks()
            dismiss()
        }

        return builder.create()
    }
}