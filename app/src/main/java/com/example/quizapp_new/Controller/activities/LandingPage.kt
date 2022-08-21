package com.example.quizapp_new.Controller.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp_new.R


class LandingPage : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)
        var btnStudentPortal = findViewById<View>(R.id.btnStudentPortal)
        var btnTeachersPortal = findViewById<View>(R.id.btnTeachersPortal)
        btnStudentPortal.setOnClickListener() {
            var i = Intent(this@LandingPage, StudentHomeActivity::class.java)
            startActivity(i)

        }
        btnTeachersPortal.setOnClickListener() {
            var i = Intent(this@LandingPage, TeachersHomeActivity::class.java)
            startActivity(i)
        }
    }
}