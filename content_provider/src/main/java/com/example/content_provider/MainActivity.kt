package com.example.content_provider

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MyTag"
    }

    private var list = arrayListOf<String>()
    private lateinit var lvContact: ListView
    private lateinit var btnShow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvContact = findViewById(R.id.lv_contact)

        btnShow = findViewById(R.id.btnShow)
        btnShow.setOnClickListener {
            showContacts()
        }
    }

    @SuppressLint("Range")
    private fun showContacts() {

        //check if permission is granted
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                999
            )
        } else {
            list.clear()

            //get contact path
            var uri = ContactsContract.Contacts.CONTENT_URI

            //use cursor to loop through each row
            var cursor = contentResolver.query(uri, null, null, null, null)
            if (cursor == null) {
                Log.i(TAG, "CURSOR IS NULL")
                return
            } else if (cursor.count <= 0) {
                Log.i(TAG, "NO DATA IN CURSOR")
            } else {
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    var id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    var name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    list.add(name)
                    cursor.moveToNext()
                }
                cursor.close()

                Log.i(TAG, "LIST: ")
                for (item: String in list) Log.i(TAG, item)

                var arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
                lvContact.adapter = arrayAdapter
            }
        }
    }

}