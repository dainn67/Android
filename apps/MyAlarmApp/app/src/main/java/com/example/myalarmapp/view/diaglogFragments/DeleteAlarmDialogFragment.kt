package com.example.myalarmapp.view.diaglogFragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.myalarmapp.R
import com.example.myalarmapp.viewmodel.MyViewModel

class DeleteAlarmDialogFragment(private val myViewModel: MyViewModel, private val position: Int): DialogFragment() {
    private lateinit var btnCancel: Button
    private lateinit var btnDelete: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myInflater = requireActivity().layoutInflater
        val view = myInflater.inflate(R.layout.delete_alarm_dialog, null, false)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)

        btnCancel = view.findViewById(R.id.btnCancel)
        btnDelete = view.findViewById(R.id.btnDelete)

        btnCancel.setOnClickListener { this.dismiss() }
        btnDelete.setOnClickListener {
            myViewModel.removeFromList(position)
            this.dismiss()
        }

        return builder.create()
    }
}