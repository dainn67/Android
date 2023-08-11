package com.example.workmanagingapp.view.addscreen

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.workmanagingapp.R

class AddScreen : AppCompatActivity() {
    private lateinit var btnBack: Button
    private lateinit var etName: EditText
    private lateinit var etContent: EditText
    private lateinit var btnChooseDate: Button
    private lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_work_layout)

        btnBack = findViewById(R.id.btnBack)
        btnBack.setOnClickListener { onBackPressed() }

        etName = findViewById(R.id.etName)
        etContent = findViewById(R.id.etContent)
        btnChooseDate = findViewById(R.id.btnChooseDate)
        btnAdd = findViewById(R.id.btnConfirmAdd)
    }
}