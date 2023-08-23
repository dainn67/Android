package com.example.workmanagingapp.view.mainscreen.works

import android.app.AlertDialog
import android.app.Dialog
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
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Constants.Companion.TAG
import com.example.workmanagingapp.model.Work
import com.example.workmanagingapp.viewmodel.MyViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myInflater = requireActivity().layoutInflater
        val view = myInflater.inflate(R.layout.dialog_viewdetail, null, false)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)

        //action buttons
        btnOk = view.findViewById(R.id.btnEditOK)
        btnChange = view.findViewById(R.id.btnEditChange)
        btnChange.isEnabled = false

        tvTitle = view.findViewById(R.id.tvViewDetailTitle)
        tvTitle.text = work.getTitle()

        tvDetailDate = view.findViewById(R.id.tvDetailDate)
        tvDetailDate.text = "Date: ${MyViewModel.displayDate(work.getTime())}"

        tvDetailTime = view.findViewById(R.id.tvDetailTime)
        tvDetailContent = view.findViewById(R.id.tvDetailContent)

        etEditContent = view.findViewById(R.id.etEditContent)

        btnEditDate = view.findViewById(R.id.btnEditDate)
        btnEditTime = view.findViewById(R.id.btnEditTime)
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

        btnOk.setOnClickListener {
            dismiss()
        }

        return builder.create()
    }

    private fun checkActionButtons(): Boolean {

        return if (isDateChanged || isTimeChanged || isContentChanged) {
            btnChange.isEnabled = true
            btnChange.setOnClickListener {
                val timeString = "${
                    tvDetailDate.text.toString().replace("Date: ", "")
                } ${tvDetailTime.text.toString().replace("Time: ", "")}:00".replace("/", "-")
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                val newTime = LocalDateTime.parse(timeString, formatter)

                val newWork = Work(work.getTitle(), newTime, etEditContent.text.toString())
                viewModel.updateWorkInList(newWork, work)
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