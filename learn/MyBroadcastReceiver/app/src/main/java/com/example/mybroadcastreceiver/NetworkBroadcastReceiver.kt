package com.example.mybroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast

class NetworkBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(checkAvailability(context))
            Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show()
    }

    private fun checkAvailability(context: Context?): Boolean{
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        //from Android M, old solution cannot be applied
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = connectivityManager.activeNetwork ?: return false
            val capability = connectivityManager.getNetworkCapabilities(network)

            return capability != null && (capability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
        }else{
            //get the network information and return its state
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}