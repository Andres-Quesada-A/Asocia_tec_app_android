package com.techsphere.asociaplan.UI.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(val minDate: Boolean=false ,val listener: (day: Int, month: Int, year: Int) -> Unit) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // aqui obtenemos la fecha actual
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        var picker = DatePickerDialog(activity as Context, this, year, month, day)
        if (minDate!=true){
            picker.datePicker.minDate = c.timeInMillis
        } else{
            picker.datePicker.maxDate = c.timeInMillis
        }
        return picker
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayofMonth: Int) {
        listener(dayofMonth, month, year)
    }


}