package com.example.myfragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class SecondFragment: Fragment() {
    private lateinit var btnMove: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_second, container, false)

        val newFrag = FirstFragment()

        btnMove = rootView.findViewById(R.id.btnMoveTo1st)
        btnMove.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager

            fragmentManager.beginTransaction().apply {
                replace(R.id.fragContainer1, newFrag)
                commit()
            }
        }

        return rootView
    }
}