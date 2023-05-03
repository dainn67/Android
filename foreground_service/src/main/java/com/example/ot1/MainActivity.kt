package com.example.ot1
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ot1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_NAME = "com.example.ot1.EXTRA_NAME"
        const val EXTRA_AGE = "com.example.ot1.EXTRA_AGE"
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val clickme = findViewById<Button>(R.id.b1);
        clickme.setOnClickListener {
            val etName = findViewById<EditText>(R.id.editTextName).text.toString()
            val etAge = findViewById<EditText>(R.id.editTextAge).text.toString().toInt()

            //pass data using intent
            val intent = Intent(this, ResultActivity::class.java)
            //intent.putExtra(EXTRA_NAME, etName)
            //intent.putExtra(EXTRA_AGE, etAge)

            //pass data using bundle
            val bundle = Bundle()
            bundle.putString("name", etName)
            bundle.putInt("age", etAge)
            intent.putExtras(bundle)

            startActivity(intent)
            Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show()

        }
    }

    //debug activity's lifecycle
    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }
}