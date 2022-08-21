package com.example.quizapp_new.Controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quizapp_new.R
import com.quizapp.database.DBHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAdd.setOnClickListener{

            //dbhelper class created with context added
            val db = DBHelper(this, null)

            // creating variables for values
            val question = edQuestion.text.toString()
            val option1 = edOption1.text.toString()
            val option2 = edOption2.text.toString()
            val option3 = edOption3.text.toString()
            val option4 = edOption4.text.toString()
            val answer=spinnerAnswer.selectedItem.toString()
            // calling method to add name to our database
            var quizid=intent.getIntExtra("quiz_id",1)
            db.addQuestions(quizid!!.toString(),question,option1,option2,option3,option4,answer)

            // Toast to message on the screen
            Toast.makeText(this, question + " added to database", Toast.LENGTH_LONG).show()

            // clearing edit texts
            edQuestion.text.clear()
            edOption1.text.clear()
            edOption2.text.clear()
            edOption3.text.clear()
            edOption4.text.clear()
        }

        btnFinish.setOnClickListener{
            startActivity(Intent(this, TeachersHomeActivity::class.java))
            finish()
        }

    }
}