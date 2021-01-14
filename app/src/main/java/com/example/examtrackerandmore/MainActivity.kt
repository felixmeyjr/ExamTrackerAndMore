package com.example.examtrackerandmore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Create an object
        lateinit var ExamDateText: TextView // lateinit: gets value later
        lateinit var ConfirmButton: Button

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ExamDateText = findViewById(R.id.ExamDateText) // Connect var with XML element
        ConfirmButton = findViewById(R.id.ConfirmExamDateBtn)

        // Define action of Button with setOnClickListener
        ConfirmButton.setOnClickListener {
            ExamDateText.text = "Hello world"
        }

    }
}