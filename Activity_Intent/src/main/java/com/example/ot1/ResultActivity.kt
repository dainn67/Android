package com.example.ot1
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

//        val intent = intent
//        var text = intent.getStringExtra(MainActivity.EXTRA_NAME)

        val bundle = intent.extras
        if (bundle != null) {
            val name = bundle.getString("name").toString()
            val age = bundle.getInt("age").toString()

            findViewById<TextView>(R.id.tv1).text = name
            findViewById<TextView>(R.id.tv2).text = age
        }
    }
}