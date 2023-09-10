package com.techsphere.asociaplan.UI.fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.techsphere.asociaplan.R
import java.util.Calendar

class TimePickerFragment(val listener: (hour: Int, minute: Int) -> Unit) : DialogFragment(),
    TimePickerDialog.OnTimeSetListener {
    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        listener(hourOfDay,minute)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        val dialog = TimePickerDialog(activity as Context,this, hour, minute, false)
        return dialog
    }
}