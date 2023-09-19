package com.example.mycoroutineapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    //https://jsonplaceholder.typicode.com/todos/1
    private val TAG = "aaa"
    private lateinit var recView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recView = findViewById(R.id.recView)
        recView.layoutManager = LinearLayoutManager(this@MainActivity)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiTODO = retrofit.create(ApiTODO::class.java)


        GlobalScope.launch (Dispatchers.IO) {
            val list = apiTODO.getTODOList().await()

            withContext(Dispatchers.Main){
                recView.adapter = TodoAdapter(this@MainActivity, list)
            }
        }
//        call.enqueue(object : Callback<List<Todo>> {
//            override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
//                Log.i(TAG, "Failure: $t")
//            }
//
//            override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
//                val todoListResponse = response.body()
//                if(todoListResponse != null){
//                    Log.i(TAG, todoListResponse.size.toString() + "\n${todoListResponse[0].title}")
//                    recView.adapter = TodoAdapter(this@MainActivity, todoListResponse)
//                }
//            }
//        })
    }
}