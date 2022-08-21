package com.example.quizapp_new.Controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp_new.databinding.ActivityStudentHomeBinding
import com.quizapp.adapters.QuestionsListTeachersAdapter
import com.quizapp.database.DBHelper

class TeachersQuestionsActivity : AppCompatActivity() {
    private var binding: ActivityStudentHomeBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate: ActivityStudentHomeBinding = ActivityStudentHomeBinding.inflate(
            layoutInflater
        )
        binding = inflate
        setContentView(inflate.root)
       quizid = intent.getIntExtra("quiz_id", 1)

        val recyclerView=binding!!.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        val db = DBHelper(this, null)
        val adap = QuestionsListTeachersAdapter(db.getQuestionListArray(quizid))
        recyclerView.adapter = adap

    }

    companion object {
        var quizid = 1
    }

}