package com.example.quizapp_new.Controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.quizapp.database.DBHelper
import kotlinx.android.synthetic.main.activity_start_quiz.*
import android.os.CountDownTimer
import com.example.quizapp_new.R
import com.example.quizapp_new.models.QuestionsModel
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class StartQuizActivity : AppCompatActivity() {
    private var mCountDown: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_quiz)
        var quizid = intent.getIntExtra("quiz_id", 1)

        val db = DBHelper(this, null)

        var questionList = db.getQuestionListArray(quizid)

        firstQues(db.getQuestionListArray(quizid))
        questionList.shuffle();

        submitbutton.setOnClickListener {
            if (answersgrp.checkedRadioButtonId == -1) {
                Toast.makeText(applicationContext, "Please select one option", Toast.LENGTH_SHORT)
                    .show()
            }
           try {
               val uans = findViewById<View>(answersgrp.checkedRadioButtonId) as RadioButton
               val ansText = uans.text.toString()

               var ans = questionList.get(flag).answer
               if (ans.equals("option1"))
                   ans = questionList.get(flag).option1
               if (ans.equals("option2"))
                   ans = questionList.get(flag).option2
               if (ans.equals("option3"))
                   ans = questionList.get(flag).option3
               if (ans.equals("option4"))
                   ans = questionList.get(flag).option4


               if (ansText == ans) {
                   correct++
                   Toast.makeText(applicationContext, "Correct", Toast.LENGTH_SHORT).show()
               } else {
                   incorrect++
                   Toast.makeText(applicationContext, "Incorrect", Toast.LENGTH_SHORT).show()
               }
               mCountDown?.cancel()
               flag++
               if (txtScore != null) txtScore.setText("Your Score : " + correct)
               if (flag < questionList.size) {
                   var quesNum= flag +1
                   tvque.text="$quesNum : ${questionList.get(flag).name}"
                   radioButton.setText(questionList.get(flag).option1)
                   radioButton2.setText(questionList.get(flag).option2)
                   radioButton3.setText(questionList.get(flag).option3)
                   radioButton4.setText(questionList.get(flag).option4)
                   timer(questionList)
               } else {
                   marks =
                       correct
                   val intent = Intent(applicationContext, ResultActivity::class.java)
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                   startActivity(intent)
               finish()
               }
               answersgrp.clearCheck()
           }catch (e:Exception){
               Toast.makeText(applicationContext, "Please select Answer", Toast.LENGTH_SHORT).show()
           }
        }

        buttonquit.setOnClickListener {
            val intent = Intent(applicationContext, ResultActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()

        }

    }

    companion object {
        var flag = 0
        var marks = 0
        var correct: Int = 0
        var incorrect: Int = 0
    }

    fun timer(questionList:ArrayList<QuestionsModel>){
        mCountDown = object : CountDownTimer(20000, 1000) {
            override fun onFinish() {
                DispName.setText("Time up!");
                flag++
                if (txtScore != null) txtScore.setText("Your Score : " + correct)
                if (flag < questionList.size) {
                    var quesNum= flag +1
                    tvque.text="$quesNum : ${questionList.get(flag).name}"
                    radioButton.setText(questionList.get(flag).option1)
                    radioButton2.setText(questionList.get(flag).option2)
                    radioButton3.setText(questionList.get(flag).option3)
                    radioButton4.setText(questionList.get(flag).option4)
                } else {
                    marks =
                        correct
                    val intent = Intent(applicationContext, ResultActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()

                }
                answersgrp.clearCheck()
                timer(questionList)
            }

            override fun onTick(millisUntilFinished: Long) {
                DispName.setText("Time left: "
                        + (millisUntilFinished / 1000).toString())
            }
        }.start()

    }

    fun firstQues(questionList :ArrayList<QuestionsModel>){
        questionList.shuffle();
        flag =0
            var quesNum= flag +1
            tvque.text="$quesNum : ${questionList.get(flag).name}"
            radioButton.setText(questionList.get(flag).option1)
            radioButton2.setText(questionList.get(flag).option2)
            radioButton3.setText(questionList.get(flag).option3)
            radioButton4.setText(questionList.get(flag).option4)
            timer(questionList)

//        }
    }
}