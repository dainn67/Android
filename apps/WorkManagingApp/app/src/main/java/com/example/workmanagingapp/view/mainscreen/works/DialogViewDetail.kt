package com.example.workmanagingapp.view.mainscreen.works

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Constants.Companion.TAG
import com.example.workmanagingapp.model.Work
import com.example.workmanagingapp.viewmodel.MyViewModel
import java.sql.Time
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlin.math.min

@RequiresApi(Build.VERSION_CODES.O)
class DialogViewDetail(
    private val work: Work,
    private val viewModel: MyViewModel
) : DialogFragment() {
    private lateinit var tvTitle: TextView
    private lateinit var tvDetailDate: TextView
    private lateinit var tvDetailTime: TextView
    private lateinit var tvDetailContent: TextView

    private lateinit var btnEditDate: Button
    private lateinit var btnEditTime: Button
    private lateinit var btnEditContent: Button
    private lateinit var btnOk: Button
    private lateinit var btnChange: Button

    private lateinit var etEditContent: EditText

    private var isDateChanged = false
    private var isTimeChanged = false
    private var isContentChanged = false

    private var newDate = LocalDateTime.now()
    private var newTime = LocalDateTime.now()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myInflater = requireActivity().layoutInflater
        val view = myInflater.inflate(R.layout.dialog_viewdetail, null, false)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)

        //action buttons
        btnOk = view.findViewById(R.id.btnEditOK)
        btnOk.setOnClickListener {
            dismiss()
        }
        btnChange = view.findViewById(R.id.btnEditChange)
        btnChange.isEnabled = false

        //view binding
        tvTitle = view.findViewById(R.id.tvViewDetailTitle)
        tvTitle.text = work.getTitle()

        tvDetailDate = view.findViewById(R.id.tvDetailDate)
        tvDetailDate.text = "Date: ${MyViewModel.displayDate(work.getTime())}"

        tvDetailTime = view.findViewById(R.id.tvDetailTime)
        tvDetailTime.text = "Time: ${MyViewModel.displayTime(work.getTime())}"

        tvDetailContent = view.findViewById(R.id.tvDetailContent)
        etEditContent = view.findViewById(R.id.etEditContent)

        //edit buttons
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        btnEditDate = view.findViewById(R.id.btnEditDate)
        btnEditDate.setOnClickListener {
            val datePickerDialog =
                DatePickerDialog(requireContext(), { _, pickedYear, pickedMonth, pickedDay ->
                    newDate = LocalDateTime.of(
                        pickedYear,
                        pickedMonth + 1,
                        pickedDay,
                        work.getTime().hour,
                        work.getTime().minute
                    )
                    tvDetailDate.text = "Date: ${MyViewModel.displayDate(newDate)}"
                    isDateChanged = newDate != work.getTime()
                    checkActionButtons()
                }, year, month, day)

            datePickerDialog.show()
        }
        btnEditTime = view.findViewById(R.id.btnEditTime)
        btnEditTime.setOnClickListener {
            val timePickerDialog = TimePickerDialog(requireContext(), { _, hour, minute ->
                newTime = LocalDateTime.of(work.getTime().year, work.getTime().month, work.getTime().dayOfMonth, hour, minute)
                tvDetailTime.text = "Time: ${MyViewModel.displayTime(hour, minute)}"
                isTimeChanged = newTime != work.getTime()
                checkActionButtons()
            }, LocalDateTime.now().hour, LocalDateTime.now().minute, true)
            timePickerDialog.show()
        }
        btnEditContent = view.findViewById(R.id.btnEditContent)
        btnEditContent.setOnClickListener {
            etEditContent.visibility = View.VISIBLE
        }
        etEditContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!s.isNullOrEmpty()) {
                    //check the button
                    isContentChanged = true
                    checkActionButtons()
                } else {
                    isContentChanged = false
                    checkActionButtons()
                }
            }
        })

        return builder.create()
    }

    private fun checkActionButtons(): Boolean {
        return if (isDateChanged || isTimeChanged || isContentChanged) {
            btnChange.isEnabled = true
            btnChange.setOnClickListener {
                val newEditedTime = LocalDateTime.of(newDate.year, newDate.month, newDate.dayOfMonth, newTime.hour, newTime.minute)
                val newWork = Work(work.getTitle(), newEditedTime, etEditContent.text.toString())
                viewModel.updateWorkInList(newWork, work)
                dismiss()
            }

            btnOk.text = "Cancel"

            true
        } else {
            btnChange.isEnabled = false
            btnOk.text = "OK"
            false
        }
    }
}