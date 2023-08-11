package com.example.workmanagingapp.view.addscreen

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.workmanagingapp.R

class DialogChooseTime: DialogFragment() {
    lateinit var timePicker: TimePicker
    lateinit var btnCancel: Button
    lateinit var btnDone: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myInflater = requireActivity().layoutInflater
        val view = myInflater.inflate(R.layout.dialog_choose_time, null, false)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)

        timePicker = view.findViewById(R.id.timePicker)
        btnCancel = view.findViewById(R.id.btnTimePickerCancel)
        btnDone = view.findViewById(R.id.btnTimePickerDone)

        //timepicker
        timePicker.setIs24HourView(true)

        //buttons
        btnCancel.setOnClickListener{
            this.dismiss()
        }

        btnDone.setOnClickListener{
            //TODO: Choose date and display to textview
            this.dismiss()
        }

        return builder.create()
    }
}