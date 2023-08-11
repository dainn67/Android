package com.example.workmanagingapp.view.addscreen

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.workmanagingapp.R

class DialogChooseDate: DialogFragment() {
    lateinit var datePicker: DatePicker
    lateinit var btnCancel: Button
    lateinit var btnDone: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myInflater = requireActivity().layoutInflater
        val view = myInflater.inflate(R.layout.dialog_choose_date, null, false)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)

        datePicker = view.findViewById(R.id.datePicker)
        btnCancel = view.findViewById(R.id.btnDatePickerCancel)
        btnDone = view.findViewById(R.id.btnDatePickerDone)

        //datepicker
        

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