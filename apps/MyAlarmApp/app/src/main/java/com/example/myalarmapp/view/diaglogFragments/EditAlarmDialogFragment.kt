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
        Log.i(TAG, "${myAlarm.getHour()}:${myAlarm.getMinute()} - ${myAlarm.getContent()} - ${myAlarm.getRepeat()}")

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
        checkboxRepeat.isChecked = myAlarm.getRepeat()

        //if user change any data, they can update the alarm
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            isTimeChanged = hourOfDay != myAlarm.getHour() || minute != myAlarm.getMinute()
            checkEnableBtn()
        }
        etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isContentChanged = etContent.text.isNotEmpty()
                checkEnableBtn()
            }
        })
        checkboxRepeat.setOnCheckedChangeListener{_, isChecked ->
            isRepeatChanged = isChecked != myAlarm.getRepeat()
            checkEnableBtn()
        }

        //buttons to change or cancel
        btnAdd.setOnClickListener {
            //update the new information
            myAlarm.setHour(timePicker.hour)
            myAlarm.setMinute(timePicker.minute)
            myAlarm.setRepeatable(checkboxRepeat.isChecked)
            if(etContent.text.isNotEmpty() && etContent.text.toString() != myAlarm.getContent()) myAlarm.setContent(etContent.text.toString())

            //update the alarm and its live data
            myViewModel.editList(myAlarm, position)
            myViewModel.getLiveDataList().value

            this.dismiss()
        }

        btnCancel.setOnClickListener { this.dismiss() }
        return builder.create()
    }

    private fun checkEnableBtn(){
        btnAdd.isEnabled = isTimeChanged || isRepeatChanged || isContentChanged
    }
}