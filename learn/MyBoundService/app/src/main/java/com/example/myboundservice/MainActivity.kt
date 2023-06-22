package com.example.myboundservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var etNum1: EditText
    private lateinit var etNum2: EditText
    private lateinit var btnCalculate: Button

    private var num1 = -1
    private var num2 = -1

    //create an instance of BService
    private var myBoundService = BService()

    private val myServiceConnection = object : ServiceConnection {

        //if the service is bound successfully
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {

            //cast the binder to BService binder to get the instance of BService
            val myBinder = iBinder as BService.MyBinder
            myBoundService = myBinder.getBService()

            //calculate sum
            val res = myBoundService.getSum(num1, num2)
            Toast.makeText(applicationContext, "Result: $res", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(name: ComponentName?) {}

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //view binding
        etNum1 = findViewById(R.id.etNum1)
        etNum2 = findViewById(R.id.etNum2)
        btnCalculate = findViewById(R.id.btnCalculate)

        btnCalculate.setOnClickListener {
            //get 2 numbers
            num1 = etNum1.text.toString().toInt()
            num2 = etNum2.text.toString().toInt()

            //create an intent and bind the service
            val intent = Intent(this, BService::class.java)
            bindService(intent, myServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(myServiceConnection)
    }
}