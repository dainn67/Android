package com.example.workmanagingapp.view.drawerscreens

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Constants
import com.example.workmanagingapp.model.Constants.Companion.TAG
import com.example.workmanagingapp.view.mainscreen.works.MyWorkListAdapter
import com.example.workmanagingapp.viewmodel.MyViewModel
import com.example.workmanagingapp.viewmodel.OnItemClickListener

class DialogViewAll(
    private val listener: OnItemClickListener,
    private val context: Context,
    private val viewModel: MyViewModel
): DialogFragment() {
    private lateinit var recViewAll: RecyclerView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflater = requireActivity().layoutInflater
        val view = layoutInflater.inflate(R.layout.view_all_work_layout, null, false)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)

        recViewAll = view.findViewById(R.id.recViewAll)
        recViewAll.layoutManager = LinearLayoutManager(context)
        recViewAll.adapter = MyWorkListAdapter(listener, context, viewModel, Constants.Companion.ViewDetailType.ALL)

        return builder.create()
    }
}