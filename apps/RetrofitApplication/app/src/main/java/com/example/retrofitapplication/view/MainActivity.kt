package com.example.retrofitapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.retrofitapplication.R
import com.example.retrofitapplication.model.Data
import com.example.retrofitapplication.viewmodel.OnClickItemListener

class MainActivity : AppCompatActivity(), OnClickItemListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onItemClick() {
        TODO("Not yet implemented")
    }

    override fun onItemLongClick() {
        TODO("Not yet implemented")
    }
}