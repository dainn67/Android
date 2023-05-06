package com.example.bound_service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MYTAG"
    }

    private var num1: Int = 0
    private var num2: Int = 0

    private var isConnected: Boolean = false

    lateinit var startBtn: Button
    lateinit var stopBtn: Button

    lateinit var etNum1: EditText
    lateinit var etNum2: EditText
    lateinit var tvRes: TextView

    var res = 0

    private var  myCalBoundService: CalculatorService = CalculatorService()
    private var calServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            Log.i(TAG, "onServiceConnected")
            var myBinder: CalculatorService.MyBinder = iBinder as CalculatorService.MyBinder
            myCalBoundService = myBinder.getCalBoundService()
            Log.i(TAG, "before sending: n1: $num1 - n2: $num2")
            res = myCalBoundService.calculate(num1, num2)
            tvRes.text = res.toString()
            isConnected = true
        }

        //this is called when the service is forced to be terminated
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i(TAG, "onServiceDisconnected")
            isConnected = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn = findViewById(R.id.startBtn)
        stopBtn = findViewById(R.id.stopBtn)

        etNum1 = findViewById(R.id.etNum1)
        etNum2 = findViewById(R.id.etNum2)
        tvRes = findViewById(R.id.tvResult)

        etNum1.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(!s.isNullOrEmpty()){
                    num1 = etNum1.text.toString().toInt()
                    Log.i(TAG, num1.toString())
                }
            }
        })

        etNum2.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(!s.isNullOrEmpty()){
                    num2 = etNum2.text.toString().toInt()
                    Log.i(TAG, num2.toString())
                }
            }
        })

        startBtn.setOnClickListener{
            Log.i(TAG, "press start")
            startTheService()
        }
        stopBtn.setOnClickListener{
            stopTheService()
            Log.i(TAG, "press stop")


        }
    }

    private fun startTheService() {
        var intent = Intent(this, CalculatorService::class.java)
        intent.putExtra("num1", num1)
        intent.putExtra("num2", num2)
        bindService(intent, calServiceConnection, Context.BIND_AUTO_CREATE)
    }

    //this is called when the service stop on purpose
    private fun stopTheService() {
        if(isConnected){
            Log.i(TAG, "unBinding")
//            unbindService(myMusicServiceConnection)
            unbindService(calServiceConnection)
            isConnected = false
        }
    }
}