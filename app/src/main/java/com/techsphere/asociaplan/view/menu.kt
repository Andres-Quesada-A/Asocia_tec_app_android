package com.techsphere.asociaplan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.techsphere.asociaplan.R

class menu : AppCompatActivity() {
    private lateinit var calendarButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        calendarButton= findViewById(R.id.button_calendario_eventos)
        calendarButton.setOnClickListener {
            startActivity(Intent(this, EventCalendarActivity::class.java))
        }
    }
}