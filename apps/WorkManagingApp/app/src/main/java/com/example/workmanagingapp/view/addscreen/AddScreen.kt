package com.example.workmanagingapp.view.addscreen

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.workmanagingapp.MainActivity
import com.example.workmanagingapp.R
import com.example.workmanagingapp.model.Constants.Companion.TAG
import com.example.workmanagingapp.model.Work
import com.example.workmanagingapp.viewmodel.MyViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class AddScreen : AppCompatActivity() {
    private lateinit var btnBack: Button
    private lateinit var btnAdd: Button

    private lateinit var etName: EditText
    private lateinit var etContent: EditText

    private lateinit var btnChooseDate: Button
    private lateinit var btnChooseTime: Button

    private lateinit var tvDate: TextView
    private lateinit var tvTime: TextView

    //variables to store selected data
    private lateinit var title: String
    private lateinit var content: String
    private lateinit var time: LocalDateTime

    private lateinit var newViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_work_layout)

        newViewModel = MyViewModel(this)

        btnBack = findViewById(R.id.btnBack)
        btnBack.setOnClickListener { onBackPressed() }

        title = ""
        content = ""

        etName = findViewById(R.id.etName)
        etName.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    Log.i(TAG, s.toString())
                    title = s.toString()
                }
            }
        )

        etContent = findViewById(R.id.etContent)
        etContent.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    Log.i(TAG, s.toString())
                    content = s.toString()
                }
            }
        )

        btnChooseDate = findViewById(R.id.btnChooseDate)
        btnChooseDate.setOnClickListener {
            val dateDialog = DialogChooseDate(newViewModel)
            dateDialog.show(supportFragmentManager, "dateDialog")
        }

        btnChooseTime = findViewById(R.id.btnChooseTime)
        btnChooseTime.setOnClickListener {
            val timeDialog = DialogChooseTime(newViewModel)
            timeDialog.show(supportFragmentManager, "timeDialog")
        }

        tvDate = findViewById(R.id.tvAddDate)
        tvTime = findViewById(R.id.tvAddTime)
        tvTime.text = "Time: " + MyViewModel.displayTime(LocalDateTime.now())
        observeChanges()

        btnAdd = findViewById(R.id.btnConfirmAdd)
        btnAdd.setOnClickListener{
            val tvDateString = tvDate.text.toString()
            val tvTimeString = tvTime.text.toString()
            val timeString = "${tvDateString.replace("Date: ", "")} ${tvTimeString.replace("Time: ", "")}:00".replace("/", "-")
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
            time = LocalDateTime.parse(timeString, formatter)

            //TODO: ("add to list and database")
            val newWork = Work(title, time, content)

            val intent = Intent(this, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("new_work", newWork)
            intent.putExtras(bundle)

            startActivity(intent)

            finish()
        }
    }

    private fun observeChanges(){
        val tvDateLiveData = newViewModel.getAddNewDateTVLiveData()
        val dateObserver = Observer<String>{newValue ->
            tvDate.text = newValue
        }
        tvDateLiveData.observe(this, dateObserver)

        val tvTimeLiveData = newViewModel.getAddNewTimeTVLiveData()
        val timeObserver = Observer<String>{newValue ->
            tvTime.text = newValue
        }
        tvTimeLiveData.observe(this, timeObserver)
    }
}