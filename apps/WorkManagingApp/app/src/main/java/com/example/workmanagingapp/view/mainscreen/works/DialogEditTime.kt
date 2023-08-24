package com.example.workmanagingapp.view.mainscreen.works

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.workmanagingapp.R

class DialogEditTime : DialogFragment() {
    private lateinit var timePicker: TimePicker
    private lateinit var btnCancel: Button
    private lateinit var btnDone: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myInflater = requireActivity().layoutInflater
        val view = myInflater.inflate(R.layout.dialog_choose_time, null, false)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)

        timePicker = view.findViewById(R.id.timePicker)
        timePicker.setIs24HourView(true)

        btnCancel = view.findViewById(R.id.btnTimePickerCancel)
        btnCancel.setOnClickListener { dismiss() }

        btnDone = view.findViewById(R.id.btnTimePickerDone)

        return builder.create()
    }
}