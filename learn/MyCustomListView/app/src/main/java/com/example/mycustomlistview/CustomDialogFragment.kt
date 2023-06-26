package com.example.mycustomlistview

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.mycustomlistview.MainActivity.Companion.addToList

class CustomDialogFragment: DialogFragment() {
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var radioMale: RadioButton
    private lateinit var radioFemale: RadioButton

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val myInflater = requireActivity().layoutInflater
        val view = myInflater.inflate(R.layout.dialog_layout, null, false)

        etName = view.findViewById(R.id.etName)
        etAge = view.findViewById(R.id.etAge)
        radioMale = view.findViewById(R.id.radioMale)
        radioFemale = view.findViewById(R.id.radioFemale)

        val builder = AlertDialog.Builder(requireActivity())
        builder
            .setView(view)
            .setTitle("Add a new person")
            .setPositiveButton("Add"){ _, _ ->
                if(etName.text.isEmpty() || etAge.text.isEmpty() || (!radioMale.isChecked && !radioFemale.isChecked))
                    Toast.makeText(context, "Please fill in all information", Toast.LENGTH_SHORT).show()
                else {
                    val name = etName.text.toString()
                    val age = etAge.text.toString().toInt()
                    val gender = if (radioMale.isChecked) Gender.MALE else Gender.FEMALE
                    addToList(User(name, age, gender))
                }
            }
            .setNegativeButton("Cancel"){dialog, _ ->
                dialog.dismiss()
            }

        return builder.create()
    }
}