package com.example.quizapp_new.Controller.activities

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp_new.R
import com.example.quizapp_new.models.Teacher
import com.quizapp.database.DBHelper

class TeacherLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginButton(view: View) {

        val message = findViewById<TextView>(R.id.textViewMessage)
        val userName = findViewById<EditText>(R.id.editTextUserName).text.toString()
        val userPassword = findViewById<EditText>(R.id.editTextPassword).text.toString()

        if (userName.isEmpty() || userPassword.isEmpty())
            Toast.makeText(this, "Please insert Username and Password", Toast.LENGTH_LONG).show()
        else {
            val myDataBase = DBHelper(this, null)
            val result = myDataBase.getTeacher(Teacher(1, "", ""))
            if (result == -1)
                message.text = "User Not Found, Please try again"
            else if (result == -2)
                message.text = "Error Cannot Open DataBase"
            else message.text = "You logged in successfully"
        }
    }
}

