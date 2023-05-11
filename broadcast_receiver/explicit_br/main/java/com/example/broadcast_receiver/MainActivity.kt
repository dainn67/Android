package com.example.broadcast_receiver

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ResolveInfo
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MyTag"
        const val MY_ACTION= "com.homesweethome.ACTION"
        const val MY_CUSTOM_ACTION= "com.homesweethome.CUSTOM_ACTION"
        const val MY_CUSTOM_ACTION2= "com.homesweethome.CUSTOM_ACTION2"
        const val MY_KEY = "com.homesweethome.KEY"
    }

    //create a dynamic broadcast receiver
    private lateinit var myBroadcastReceiver: MyBroadcastReceiver

    //create a custom broadcast receiver
    private lateinit var customBroadcastReceiver: CustomBroadcastReceiver

    //create a custom broadcast receiver sending objects
    lateinit var objectBroadcasrReceiver: ObjectBroadcastReceiver

    //create a explicit broadcast receiver
    lateinit var explicitBroadCastReceiver: ExplicitBroadCastReceiver

    lateinit var btnSend : Button
    lateinit var btnGet : Button
    lateinit var tvRes : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //instantiate the dynamic BR
        myBroadcastReceiver = MyBroadcastReceiver()

        //instantiate the custom BR
        customBroadcastReceiver = CustomBroadcastReceiver()

        //instantiate the custom BR sending objects
        objectBroadcasrReceiver = ObjectBroadcastReceiver()

        //instantiate the explicit BR
        explicitBroadCastReceiver = ExplicitBroadCastReceiver()

        btnSend = findViewById(R.id.btn_send)
        btnGet = findViewById(R.id.btn_receive)
        tvRes = findViewById(R.id.tv_result)

        btnSend.setOnClickListener{
//            clickSendCustomBroadcast()
//            clickSendObject()
            clickSendExplicit(this)
        }

        btnGet.setOnClickListener{
            receiveBroadcastData()
        }

    }

    private fun clickSendExplicit(context: Context) {
        //if send to current app's BR
//        var intent = Intent(this, ExplicitBroadCastReceiver::class.java)
//        sendBroadcast(intent)

        //if send to other apps, use component name (data passing in is similar to intent because in the end they create a componentName
//        var intent = Intent()
//        var componentName = ComponentName("com.example.br_receive_app", "com.example.br_receive_app.ExplicitBroadcastReceiver")
//        intent.component = componentName
//        sendBroadcast(intent)

        //if send to multiple projects
        var intent = Intent(MY_ACTION)
        intent.putExtra(MY_KEY, "Konichiwa")
        var packageManager = packageManager
        var resolveInfoList: MutableList<ResolveInfo> = packageManager.queryBroadcastReceivers(intent, 0)
        for(resolveInfo: ResolveInfo in resolveInfoList){
            var componentName = ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name)
            intent.component = componentName
            sendBroadcast(intent)
        }

    }

    private fun clickSendObject() {
        var u1 = User("Dai", 20)
        var u2 = User("Huy", 20)

        var gson =Gson()

        var u1Json = gson.toJson(u1)

        var userList: MutableList<User> = mutableListOf()
        userList.add(u1)
        userList.add(u2)
        var jsonList = gson.toJsonTree(userList).toString()

        var intent = Intent(MY_CUSTOM_ACTION2)

        //if send an object
//        intent.putExtra(MY_KEY, u1Json)

        //if send a list of objects
        intent.putExtra(MY_KEY, jsonList)


        sendBroadcast(intent)
    }

    private fun clickSendCustomBroadcast() {
        var intent = Intent(MY_CUSTOM_ACTION)
        intent.putExtra("text", "Konichiwa")
        sendBroadcast(intent)
    }

    private fun receiveBroadcastData(){
        tvRes.text = customBroadcastReceiver.returnRes()
    }

    override fun onStart() {
        super.onStart()

//        register the receiver using intentFilter to check CONNECTIVITY_ACTION
//        var intentFilterDynamic = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
//        registerReceiver(myBroadcastReceiver, intentFilterDynamic)
//        Log.i(TAG, "onStart - registered dynamic br")

        var intentFilterCustom = IntentFilter(MY_CUSTOM_ACTION)
        registerReceiver(customBroadcastReceiver, intentFilterCustom)
        Log.i(TAG, "onStart - registered custom br")

        var intentFilterObjects = IntentFilter(MY_CUSTOM_ACTION2)
        registerReceiver(objectBroadcasrReceiver, intentFilterObjects)
        Log.i(TAG, "onStart - registered object broadcast receiver")
    }

    override fun onStop() {
//        unregisterReceiver(myBroadcastReceiver)
        unregisterReceiver(customBroadcastReceiver)
        unregisterReceiver(objectBroadcasrReceiver)
        Log.i(TAG, "onStop - unregistered")
        super.onStop()
    }
}
/**
//IN THE OTHER APP, TO RECEIVE DATA FROM BROADCAST RECEIVER (A STRING, TO BE SPECIFIC), CREATE AN INSTANCE OF IT, REGISTER AND USE LIKE NORMAL

    inner class CustomBroadcastReceiver : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent != null && intent.action == MY_CUSTOM_ACTION){

                receiving data logic here
                var resString = intent.getStringExtra("data")
                tvRes.text = resString
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customBR = CustomBroadcastReceiver()
        tvRes = findViewById(R.id.tv_res)
    }

    override fun onStart() {
        super.onStart()
        var intentFilter = IntentFilter(MY_CUSTOM_ACTION)
        registerReceiver(customBR, intentFilter)
    }

    override fun onDestroy() {
        unregisterReceiver(customBR)
        super.onDestroy()
    }

 //PUT THIS IN MAINACTIVITY AND RUN EXAMPLE
*/