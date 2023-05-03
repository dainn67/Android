package com.example.ot1

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ot1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_NAME = "com.example.ot1.EXTRA_NAME"
        const val EXTRA_AGE = "com.example.ot1.EXTRA_AGE"
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.b1.setOnClickListener{
            start_stopService()
        }
//
//        val clickme = findViewById<Button>(R.id.b1);
//        clickme.setOnClickListener {
//            val etName = findViewById<EditText>(R.id.editTextName).text.toString()
//            val etAge = findViewById<EditText>(R.id.editTextAge).text.toString().toInt()
//
//            val intent = Intent(this, ResultActivity::class.java)
////            intent.putExtra(EXTRA_NAME, etName)
////            intent.putExtra(EXTRA_AGE, etAge)
//
//            val bundle = Bundle()
//            bundle.putString("name", etName)
//            bundle.putInt("age", etAge)
//            intent.putExtras(bundle)
//
//            startActivity(intent)
//            Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show()

    }

    private fun start_stopService(){
        if(isRunning(MusicActivity::class.java)){
            Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show()
            stopService(Intent(this, MusicActivity::class.java))
        }else{
            Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show()
            startService(Intent(this, MusicActivity::class.java))
        }
    }

    private fun isRunning(mClass: Class<MusicActivity>):Boolean{
        val manager: ActivityManager = getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager
        for(service: ActivityManager.RunningServiceInfo in manager.getRunningServices(Integer.MAX_VALUE)){
            if(mClass.name.equals(service.service.className)){
                return true
            }
        }
        return false
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

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }
}