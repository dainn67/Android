package com.example.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class MyFragment1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_my1, container, false)

        val myFragment2 = MyFragment2()

        var btnChange = rootView.findViewById<Button>(R.id.btnChange1)

        var isCreated = false

        btnChange.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager

            if(!isCreated){
                Toast.makeText(context, "Press 1", Toast.LENGTH_SHORT).show()
                fragmentManager.beginTransaction().apply {
                    add(R.id.frag_container2, myFragment2, "frag2")
                    commit()
                }
                isCreated = true
            }else{
                val searchFragment = fragmentManager.findFragmentByTag("frag2")
                if (searchFragment != null) {
                    fragmentManager.beginTransaction().remove(searchFragment).commit()
                    isCreated = false
                }
            }
        }

        return rootView
    }
}