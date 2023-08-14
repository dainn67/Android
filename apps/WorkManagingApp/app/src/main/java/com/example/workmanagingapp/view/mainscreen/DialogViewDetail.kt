package com.example.workmanagingapp.view.mainscreen

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Work

class DialogViewDetail(
    private val work: Work
): DialogFragment() {
    private lateinit var tvTitle: TextView
    private lateinit var tvDetailDate: TextView
    private lateinit var tvDetailTime: TextView
    private lateinit var tvDetailContent: TextView

    private lateinit var btnEditDate: Button
    private lateinit var btnEditTime: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myInflater = requireActivity().layoutInflater
        val view = myInflater.inflate(R.layout.dialog_viewdetail, null, false)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)

        tvTitle = view.findViewById(R.id.tvViewDetailTitle)
        tvTitle.text = work.getTitle()

        tvDetailDate = view.findViewById(R.id.tvDetailDate)
        tvDetailDate.text = "Date: ${work.getTime().date}/${work.getTime().month + 1}"

        tvDetailTime = view.findViewById(R.id.tvDetailTime)
        tvDetailContent = view.findViewById(R.id.tvDetailContent)

        btnEditDate = view.findViewById(R.id.btnEditDate)
        btnEditTime = view.findViewById(R.id.btnEditTime)

        return builder.create()
    }
}