package com.example.receive_data_app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class CustomBroadcastReceiver: BroadcastReceiver() {
    private var receivedJson = ""
    private var receivedListJson = ""

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i(MainActivity.TAG, "onReceive")
        if(intent != null && intent.action == MainActivity.MY_CUSTOM_ACTION){
            var jsonn = intent.getStringExtra(MainActivity.MY_KEY)
            if (jsonn != null) {
//                receivedJson = jsonn
                receivedListJson = jsonn
            }else{
                Log.i(MainActivity.TAG, "Nothing")
            }
        }
    }

    //if data is sent is an object
    fun getUserData(): User {
        var gson = Gson()
        return gson.fromJson(receivedJson, User::class.java) ?: User("Unknown", 0)
    }

    //if data is sent is a list of objects
    fun getListOfUser(): MutableList<User>{
        var resList: MutableList<User> = mutableListOf()
        var jsonArray = JSONArray(receivedListJson)
        var jsonObject: JSONObject
        var gson = Gson()

        for(i in 0 until jsonArray.length()){
            jsonObject = jsonArray.getJSONObject(i)
            var tmpUser: User = gson.fromJson(jsonObject.toString(), User::class.java)
            resList.add(tmpUser)
        }
        return resList
    }
}