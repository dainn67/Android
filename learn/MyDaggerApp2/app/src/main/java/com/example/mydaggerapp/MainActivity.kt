package com.example.mydaggerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mydaggerapp.api.ApiUser
import com.example.mydaggerapp.api.UserResponse
import com.example.mydaggerapp.di.MyApplication
import com.example.mydaggerapp.model.Constants.Companion.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var apiUser: ApiUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appComponent = (application as MyApplication).myAppComponent
        appComponent.injectActivity(this)

        apiUser.getUsers(20).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    val userList = userResponse?.results!!
                    Log.i(TAG, userList.size.toString())
                } else {
                    val errorResponseBody = response.errorBody()?.string()
                    Log.e(TAG,"HTTP Error Code: ${response.code()}\nError Response Body: $errorResponseBody")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.i(TAG, "Failed: $t")
            }
        })
    }
}