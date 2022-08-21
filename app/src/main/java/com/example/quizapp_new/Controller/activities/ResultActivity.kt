package com.example.quizapp_new.Controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizapp_new.R
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)


        val sb = StringBuffer()
        sb.append(" Correct answers: ${StartQuizActivity.correct.toString()} \n")
        val sb2 = StringBuffer()
        sb2.append(" Incorrect Answers: ${StartQuizActivity.incorrect.toString()} \n")
        val sb3 = StringBuffer()
        sb3.append(" Final Score: ${StartQuizActivity.correct.toString()}\n")
        tvres.setText(sb)
        tvres2.setText(sb2)
        tvres3.setText(sb3)

        StartQuizActivity.correct = 0
        StartQuizActivity.incorrect = 0

        btnRestart.setOnClickListener{
            val `in` = Intent(applicationContext, StudentHomeActivity::class.java)
            startActivity(`in`)
            finish()
        }
    }
}