package com.example.myalarmapp.view.diaglogFragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.myalarmapp.R
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.models.Constants.Companion.TAG
import com.example.myalarmapp.viewmodel.MyViewModel

class EditAlarmDialogFragment(
    private val myAlarm: Alarm,
    private val position: Int,
    private val myViewModel: MyViewModel
) : DialogFragment() {
    private lateinit var timePicker: TimePicker
    private lateinit var etContent: EditText
    private lateinit var checkboxRepeat: CheckBox
    private lateinit var btnCancel: Button
    private lateinit var btnAdd: Button

    private var isTimeChanged = false
    private var isContentChanged = false
    private var isRepeatChanged = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //debug
        Log.i(TAG, "${myAlarm.getHour()}:${myAlarm.getMinute()} - ${myAlarm.getContent()} - ${myAlarm.getRepeatable()}")

        val myInflater = requireActivity().layoutInflater
        val view = myInflater.inflate(R.layout.fragment_add_alarm_dialog, null, false)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)

        //view binding
        timePicker = view.findViewById(R.id.timePicker)
        timePicker.setIs24HourView(true)
        etContent = view.findViewById(R.id.etContent)
        checkboxRepeat = view.findViewById(R.id.checkboxRepeat)
        btnCancel = view.findViewById(R.id.btnCancel)
        btnAdd = view.findViewById(R.id.btnAdd)
        btnAdd.text = "Change"
        btnAdd.isEnabled = false

        //set timePicker to item's time
        timePicker.currentHour = myAlarm.getHour()
        timePicker.currentMinute = myAlarm.getMinute()
        etContent.hint = myAlarm.getContent()
        checkboxRepeat.isChecked = myAlarm.getRepeatable()

        //if user change any data, they can update the alarm
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            isTimeChanged = hourOfDay != myAlarm.getHour() || minute != myAlarm.getMinute()
            btnAdd.isEnabled = isTimeChanged || isRepeatChanged || isContentChanged
        }
        etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isContentChanged = etContent.text.isNotEmpty()
                btnAdd.isEnabled = isTimeChanged || isRepeatChanged || isContentChanged
            }
        })
        checkboxRepeat.setOnCheckedChangeListener{_, isChecked ->
            isRepeatChanged = isChecked != myAlarm.getRepeatable()
            btnAdd.isEnabled = isTimeChanged || isRepeatChanged || isContentChanged
        }

        //buttons to change or cancel
        btnCancel.setOnClickListener {
            this.dismiss()
        }

        btnAdd.setOnClickListener {
            //update the new information
            if(timePicker.hour != myAlarm.getHour()) myAlarm.setHour(timePicker.hour)
            if(timePicker.minute != myAlarm.getMinute()) myAlarm.setMinute(timePicker.minute)
            Log.i(TAG, "${if(etContent.text.isEmpty()) "etContent empty" else etContent.text.toString()} - ${myAlarm.getContent()}")
            if(etContent.text.isNotEmpty() && etContent.text.toString() != myAlarm.getContent()) myAlarm.setContent(etContent.text.toString())
            if(checkboxRepeat.isChecked != myAlarm.getRepeatable()) myAlarm.setRepeatable(checkboxRepeat.isChecked)

            //update the alarm and its live data
            myViewModel.editList(myAlarm, position)
            myViewModel.getLiveDataList().value

            this.dismiss()
        }

        return builder.create()
    }
}