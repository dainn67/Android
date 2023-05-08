package com.example.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        //if receiving request to check WIFI (in intent?.action), then check
        if(ConnectivityManager.CONNECTIVITY_ACTION == intent?.action){

            //create a function isAvailable to check
            if (isAvailable(context)){
                Toast.makeText(context, "AVAILABLE", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "UNAVAILABLE", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun isAvailable(context: Context?): Boolean {
        var connectivityManager : ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(connectivityManager == null) return false

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            var network = connectivityManager.activeNetwork
            if (network == null) return false

            var capibility = connectivityManager.getNetworkCapabilities(network)
            return  capibility != null && capibility.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }
        else{   //under Android M -> check differently
            var networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}