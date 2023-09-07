package com.example.retrofitapplication.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.retrofitapplication.R
import com.example.retrofitapplication.model.User
import com.example.retrofitapplication.viewmodel.MyViewModel

class DialogDetail(
    private val user: User
): DialogFragment() {
    private lateinit var ivGender: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvID: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvDOB: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvLogin: TextView
    private lateinit var tvRegister: TextView
    private lateinit var tvContact: TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.item_person_detail, null, false)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)

        //view binding
        ivGender = view.findViewById(R.id.ivDetail)
        ivGender.setImageResource(if(user.gender == "female") R.drawable.female else R.drawable.male)

        tvTitle = view.findViewById(R.id.tvDetailTitle)
        tvTitle.text = MyViewModel.displayName(user.name)

        tvID = view.findViewById(R.id.tvDetailID)
        tvID.text = MyViewModel.displayID(user.id)

        tvLocation = view.findViewById(R.id.tvDetailLocation)
        tvLocation.text = MyViewModel.displayLocation(user.location)

        tvDOB = view.findViewById(R.id.tvDetailDOB)
        tvDOB.text = MyViewModel.displayDOB(user.dob)

        tvEmail = view.findViewById(R.id.tvDetailEmail)
        tvEmail.text = user.email

        tvLogin = view.findViewById(R.id.tvDetailLogin)
        tvLogin.text = MyViewModel.displayLogin(user.login)

        tvRegister = view.findViewById(R.id.tvDetailRegister)
        tvRegister.text = MyViewModel.displayRegister(user.registered)

        tvContact = view.findViewById(R.id.tvDetailContact)
        tvContact.text = MyViewModel.displayContact(user.phone, user.cell)

        return builder.create()
    }
}