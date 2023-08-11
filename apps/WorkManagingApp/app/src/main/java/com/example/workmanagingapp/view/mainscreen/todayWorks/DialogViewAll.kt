package com.example.workmanagingapp.view.mainscreen.todayWorks

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R

class DialogViewAll(
    private val title: String
): DialogFragment() {
    lateinit var tvTitle: TextView
    lateinit var recView: RecyclerView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myInflater = requireActivity().layoutInflater
        val view = myInflater.inflate(R.layout.dialog_viewall, null, false)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)

        tvTitle = view.findViewById(R.id.tvViewAllTitle)
        tvTitle.text = title

        recView = view.findViewById(R.id.rvViewAll)


        return builder.create()
    }
}