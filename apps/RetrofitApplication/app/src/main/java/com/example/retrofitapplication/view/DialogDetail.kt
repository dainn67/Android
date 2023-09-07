package com.example.retrofitapplication.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.retrofitapplication.R
import com.example.retrofitapplication.model.Constants
import com.example.retrofitapplication.model.User
import com.example.retrofitapplication.model.UserDob
import com.example.retrofitapplication.model.UserId
import com.example.retrofitapplication.model.UserLocation
import com.example.retrofitapplication.model.UserLogin
import com.example.retrofitapplication.model.UserName
import com.example.retrofitapplication.model.UserRegistered
import com.example.retrofitapplication.viewmodel.MyViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
        tvTitle.text = displayName(user.name)

        tvID = view.findViewById(R.id.tvDetailID)
        tvID.text = displayID(user.id)

        tvLocation = view.findViewById(R.id.tvDetailLocation)
        tvLocation.text = displayLocation(user.location)

        tvDOB = view.findViewById(R.id.tvDetailDOB)
        tvDOB.text = displayDOB(user.dob)

        tvEmail = view.findViewById(R.id.tvDetailEmail)
        tvEmail.text = user.email

        tvLogin = view.findViewById(R.id.tvDetailLogin)
        tvLogin.text = displayLogin(user.login)

        tvRegister = view.findViewById(R.id.tvDetailRegister)
        tvRegister.text = displayRegister(user.registered)

        tvContact = view.findViewById(R.id.tvDetailContact)
        tvContact.text = displayContact(user.phone, user.cell)

        return builder.create()
    }

    private fun displayName(name: UserName): String{
        with(name){
            return "$title. $first $last"
        }
    }

    private fun displayID(id: UserId): String{
        return "Name: ${id.name}\nValue: ${id.value}"
    }

    private fun displayLocation(location: UserLocation): String{
        with(location){
            return "${street.number} ${street.name}, $city, $state, $country"
        }
    }

    private fun displayLogin(login: UserLogin): String{
        with(login){
            return "UUID: $uuid\n\nUsername: $username\n\nPassword: $password\n\nSalt: $salt\n\nmd5: $md5\n\nsha1: $sha1\n\nsha256: $sha256"
        }
    }

    private fun displayDOB(dob: UserDob): String{
        Log.i(Constants.TAG, dob.date)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(dob.date)

        return "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)+1}/${calendar.get(
            Calendar.YEAR)}\n${dob.age} years old"
    }

    private fun displayRegister(registered: UserRegistered): String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(registered.date)

        return "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)}/${calendar.get(
            Calendar.YEAR)}\nAge: ${registered.age}"
    }

    private fun displayContact(phone: String, cell: String): String{
        return "Phone: $phone\nCell: $cell"
    }
}