package com.example.myalarmapp.view

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.example.myalarmapp.R
import com.example.myalarmapp.models.Alarm
import com.example.myalarmapp.view.diaglogFragments.DeleteAlarmDialogFragment
import com.example.myalarmapp.view.diaglogFragments.EditAlarmDialogFragment
import com.example.myalarmapp.viewmodel.MyViewModel

class AlarmAdapter(
    private val context: Context,
    private val id: Int,
    private val list: List<Alarm>,
    private val myViewModel: MyViewModel,
    private val supportFragmentManager: FragmentManager
) : ArrayAdapter<Alarm>(context, id, list) {
    private lateinit var llItem: LinearLayout
    private lateinit var tvTime: TextView
    private lateinit var tvContent: TextView
    private lateinit var tvRepeated: TextView
    private lateinit var swOnOff: Switch

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val myInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = myInflater.inflate(id, null, false)

        //bind data
        llItem = view.findViewById(R.id.llItem)
        tvTime = view.findViewById(R.id.tvTime)
        tvContent = view.findViewById(R.id.tvContent)
        tvRepeated = view.findViewById(R.id.tvRepeat)
        swOnOff = view.findViewById(R.id.switchOnOff)

        val myAlarm = list[position]

        val hourFormat: String = if(myAlarm.getHour() < 10) "0${myAlarm.getHour()}" else myAlarm.getHour().toString()
        val minuteFormat: String = if(myAlarm.getMinute() < 10) "0${myAlarm.getMinute()}" else myAlarm.getMinute().toString()
        tvTime.text = "$hourFormat:$minuteFormat"
        tvContent.text = myAlarm.getContent()
        tvRepeated.text = if(myAlarm.getRepeatable()) "Repeated" else ""
        swOnOff.isChecked = myAlarm.getState()

        llItem.setOnClickListener{
            val dialog = EditAlarmDialogFragment(myAlarm, position, myViewModel)
            dialog.show(supportFragmentManager, "editDialog")
        }

        llItem.setOnLongClickListener {
            val dialog = DeleteAlarmDialogFragment(myViewModel, position)
            dialog.show(supportFragmentManager, "deleteDialog")
            true
        }

        swOnOff.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked) myViewModel.getScheduler().schedule(list[position], position)
            else myViewModel.getScheduler().cancel(list[position])
        }

        return view
    }
}