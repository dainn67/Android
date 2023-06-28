package com.example.myalarmapp.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.myalarmapp.R
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.viewmodel.MyViewModel

class AddAlarmDialogFragment(private val myViewModel: MyViewModel) : DialogFragment() {
    private lateinit var timePicker: TimePicker
    private lateinit var btnCancel: Button
    private lateinit var btnAdd: Button
    private lateinit var etContent: EditText
    private lateinit var checkboxRepeat: CheckBox

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myInflater = requireActivity().layoutInflater
        val view = myInflater.inflate(R.layout.fragment_add_alarm_dialog, null, false)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)

        timePicker = view.findViewById(R.id.timePicker)
        etContent = view.findViewById(R.id.etContent)
        btnCancel = view.findViewById(R.id.btnCancel)
        btnAdd = view.findViewById(R.id.btnAdd)
        checkboxRepeat = view.findViewById(R.id.checkboxRepeat)

        btnCancel.setOnClickListener{
            this.dismiss()
        }
        btnAdd.setOnClickListener {
            val hour = timePicker.currentHour
            val minute = timePicker.currentMinute
            val content = etContent.text.toString()
            val isRepeated = checkboxRepeat.isChecked

            myViewModel.addToList(Alarm(hour, minute, content, isRepeated))
        }

        return builder.create()
    }
}