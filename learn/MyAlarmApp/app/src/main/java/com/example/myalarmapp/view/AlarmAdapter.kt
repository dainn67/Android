package com.example.myalarmapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.myalarmapp.R
import com.example.myalarmapp.models.Alarm

class AlarmAdapter(
    private val context: Context,
    private val id: Int,
    private val list: List<Alarm>
) : ArrayAdapter<Alarm>(context, id, list) {
    private lateinit var tvTime: TextView
    private lateinit var tvContent: TextView
    private lateinit var tvRepeated: TextView

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val myInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = myInflater.inflate(id, null, false)

        tvTime = view.findViewById(R.id.tvTime)
        tvContent = view.findViewById(R.id.tvContent)
        tvRepeated = view.findViewById(R.id.tvRepeat)

        val myAlarm = list[position]

        val hourFormat: String = if(myAlarm.getHour() < 10) "0${myAlarm.getHour()}" else myAlarm.getHour().toString()
        val minuteFormat: String = if(myAlarm.getMinute() < 10) "0${myAlarm.getMinute()}" else myAlarm.getMinute().toString()
        tvTime.text = "$hourFormat:$minuteFormat"
        tvContent.text = myAlarm.getContent()
        tvRepeated.text = if(myAlarm.getRepeatable()) "Repeated" else ""

        return view
    }
}