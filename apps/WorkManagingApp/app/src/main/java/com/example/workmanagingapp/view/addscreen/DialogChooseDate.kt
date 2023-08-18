package com.example.workmanagingapp.view.addscreen

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.workmanagingapp.R
import com.example.workmanagingapp.viewmodel.MyViewModel

class DialogChooseDate(
    private val viewModel: MyViewModel
): DialogFragment() {
    lateinit var datePicker: DatePicker
    lateinit var btnCancel: Button
    lateinit var btnDone: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myInflater = requireActivity().layoutInflater
        val view = myInflater.inflate(R.layout.dialog_choose_date, null, false)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)

        datePicker = view.findViewById(R.id.datePicker)
        btnCancel = view.findViewById(R.id.btnDatePickerCancel)
        btnDone = view.findViewById(R.id.btnDatePickerDone)

        //buttons
        btnCancel.setOnClickListener{
            this.dismiss()
        }

        btnDone.setOnClickListener{
            val displayDay = if(datePicker.dayOfMonth < 10) "0${datePicker.dayOfMonth}" else datePicker.dayOfMonth.toString()
            val displayMonth = if(datePicker.month + 1 < 10) "0${datePicker.month + 1}" else (datePicker.month + 1).toString()
            viewModel.getAddNewDateTVLiveData().value = "Date: $displayDay/$displayMonth/${datePicker.year}"
            this.dismiss()
        }

        return builder.create()
    }
}