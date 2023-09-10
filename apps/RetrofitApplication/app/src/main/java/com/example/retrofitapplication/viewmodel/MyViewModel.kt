package com.example.retrofitapplication.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitapplication.api.ApiUser
import com.example.retrofitapplication.api.UserResponse
import com.example.retrofitapplication.model.Constants.Companion.TAG
import com.example.retrofitapplication.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyViewModel(
    private val context: Context
) : ViewModel() {
    private var userList: MutableList<User>
    private var userListLiveData = MutableLiveData<MutableList<User>>()

    //create the service
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiUser = retrofit.create(ApiUser::class.java)

    init {
        userList = mutableListOf()
        userListLiveData = MutableLiveData()
        userListLiveData.value = userList
    }

    //getters
    fun getUserList() = userList
    fun getUserListLiveData() = userListLiveData

    fun getUsersFromURL(amount: Int) {
        val call = apiUser.getUsers(amount)
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    userList = userResponse?.results!!
                    userListLiveData.value = userList
                    Toast.makeText(context, "Loading successfully", Toast.LENGTH_SHORT).show()

                    Log.i(TAG, "${userResponse.info.seed}\n${userResponse.info.page}\n${userResponse.info.results}\n${userResponse.info.version}")
                } else {
                    val errorResponseBody = response.errorBody()?.string()
                    Log.e(TAG,"HTTP Error Code: ${response.code()}\nError Response Body: $errorResponseBody")
                    Toast.makeText(context, "No response", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.i(TAG, "Failed: $t")
                Toast.makeText(context, "Loading failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}