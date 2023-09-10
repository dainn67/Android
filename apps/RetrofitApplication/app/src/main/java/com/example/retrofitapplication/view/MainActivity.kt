package com.example.retrofitapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitapplication.R
import com.example.retrofitapplication.model.Constants.Companion.TAG
import com.example.retrofitapplication.model.User
import com.example.retrofitapplication.viewmodel.MyViewModel
import com.example.retrofitapplication.viewmodel.OnClickItemListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OnClickItemListener {
    @Inject
    lateinit var viewmodelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MyViewModel
    private lateinit var recView: RecyclerView
    private lateinit var btnLoad: FloatingActionButton

    private lateinit var userListLiveData: MutableLiveData<MutableList<User>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, viewmodelFactory)[MyViewModel::class.java]
//        viewModel = MyViewModel(this)

        recView = findViewById(R.id.rvUserList)
        recView.layoutManager = LinearLayoutManager(this)
        recView.adapter = MyAdapter(this, viewModel.getUserList(), this)

        userListLiveData = viewModel.getUserListLiveData()
        val observer = Observer<MutableList<User>>{newList ->
            recView.adapter = MyAdapter(this, newList, this)
        }
        userListLiveData.observe(this, observer)

        btnLoad = findViewById(R.id.btnLoad)
        btnLoad.setOnClickListener{
            viewModel.getUsersFromURL(20)
        }
    }

    override fun onItemClick(position: Int) {
        val dialog = DialogDetail(viewModel.getUserList()[position])
        dialog.show(supportFragmentManager, "dialog_detail")
    }
}