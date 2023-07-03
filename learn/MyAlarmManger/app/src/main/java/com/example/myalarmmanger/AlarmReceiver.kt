package com.example.myalarmmanger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.myalarmmanger.MainActivity.Companion.TAG

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("content") ?: return
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        Log.i(TAG, "PO PI PO PI PO PO PI PO")
    }
}