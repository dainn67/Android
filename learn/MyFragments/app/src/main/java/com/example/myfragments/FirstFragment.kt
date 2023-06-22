package com.example.myfragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer

class FirstFragment : Fragment() {
    companion object{
        const val TAG = "MY-TAG"
    }

    private lateinit var btnMove: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_first, container, false)

        val newFrag = SecondFragment()

        btnMove = rootView.findViewById(R.id.btnMoveTo2nd)
        btnMove.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager

            fragmentManager.beginTransaction().apply {
                replace(R.id.fragContainer1, newFrag)
                commit()
            }

        }

        return rootView
    }

    /*
    * Lifecycle:
    * onAttach: attach fragment to parent activity
    * onCreate: create the fragment
    * onCreateView: create the UI and inflate them
    * onStart: fragment is now visible
    * onResume: fragment is now in foreground and interactive
    * onPause: fragment is not on focus but still visible
    * onStop: fragment is not visible
    * onDestroyView: UI is now destroyed
    * onDestroy: fragment is now destroyed
    * onDetach: fragment is detach from activity
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.i(TAG, "onViewStateRestored")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSavedInstance")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }
}