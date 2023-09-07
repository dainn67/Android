package com.example.retrofitapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitapplication.api.ApiService
import com.example.retrofitapplication.api.UserResponse
import com.example.retrofitapplication.model.Constants.Companion.TAG
import com.example.retrofitapplication.model.Data
import com.example.retrofitapplication.model.User
import com.example.retrofitapplication.model.UserDob
import com.example.retrofitapplication.model.UserId
import com.example.retrofitapplication.model.UserLocation
import com.example.retrofitapplication.model.UserLogin
import com.example.retrofitapplication.model.UserName
import com.example.retrofitapplication.model.UserRegistered
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MyViewModel(

) : ViewModel() {
    private var userList: List<User>
    private var tmpList = mutableListOf<String>()
    private var userListLiveData = MutableLiveData<List<User>>()
    private var tmpListLiveData = MutableLiveData<List<String>>()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //create the service
    private val apiService = retrofit.create(ApiService::class.java)

    init {
        val data = Data()
        userList = data.getList()
        userListLiveData = MutableLiveData()
        userListLiveData.value = userList

        tmpListLiveData = MutableLiveData()
        tmpListLiveData.value = tmpList
    }

    //getters
    fun getUserList() = userList
    fun getUserListLiveData() = userListLiveData
    fun getTmpListLiveData() = tmpListLiveData

    //setters
    fun setUserList(newList: List<User>) {
        userList = newList
        userListLiveData.value = userList
    }

    companion object{
        fun displayName(name: UserName): String{
            with(name){
                return "$title. $first $last"
            }
        }

        fun displayID(id: UserId): String{
            return "Name: ${id.name}\nValue: ${id.value}"
        }

        fun displayLocation(location: UserLocation): String{
            with(location){
                return "${street.number} ${street.name}, $city, $state, $country"
            }
        }

        fun displayLogin(login: UserLogin): String{
            with(login){
                return "UUID: $uuid\n\nUsername: $username\n\nPassword: $password\n\nSalt: $salt\n\nmd5: $md5\n\nsha1: $sha1\n\nsha256: $sha256"
            }
        }

        fun displayDOB(dob: UserDob): String{
            Log.i(TAG, dob.date)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.time = dateFormat.parse(dob.date)

            return "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)+1}/${calendar.get(Calendar.YEAR)}\n${dob.age} years old"
        }

        fun displayRegister(registered: UserRegistered): String{
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val calendar = Calendar.getInstance()
            calendar.time = dateFormat.parse(registered.date)

            return "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)}/${calendar.get(Calendar.YEAR)}\nAge: ${registered.age}"
        }

        fun displayContact(phone: String, cell: String): String{
            return "Phone: $phone\nCell: $cell"
        }
    }

    fun getUsersFromURL(amount: Int) {
        val call = apiService.getUsers(amount)
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    val requestUrl = call.request().url().toString()
                    userList = userResponse?.results!!
                    Log.i(
                        TAG,
                        "URL: $requestUrl\nBody: ${response.body()}" + "\nSize: ${userList?.size}"
                    )

                    userListLiveData.value = userList
                    tmpList.add("hehe")
                    tmpListLiveData.value = tmpList
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