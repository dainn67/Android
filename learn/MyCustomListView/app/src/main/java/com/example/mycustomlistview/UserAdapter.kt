package com.example.mycustomlistview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class UserAdapter(private val context: Context, private val id: Int, private val list: MutableList<User>): ArrayAdapter<User>(context, id, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val myInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = convertView?: myInflater.inflate(id, null, false)

        val user = list[position]
        view.findViewById<TextView>(R.id.tvName).text = "Name: ${user.getName()}"
        view.findViewById<TextView>(R.id.tvAge).text = "Age: ${user.getAge()}"
        view.findViewById<TextView>(R.id.tvGender).text = "Gender: ${user.getGender()}"

        return view
    }
}