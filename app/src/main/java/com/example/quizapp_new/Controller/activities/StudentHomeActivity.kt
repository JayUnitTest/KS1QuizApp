package com.example.quizapp_new.Controller.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp_new.databinding.ActivityStudentHomeBinding
import com.quizapp.adapters.QuizListAdapterStudent
import com.quizapp.database.DBHelper


class StudentHomeActivity : AppCompatActivity() {
    private var binding: ActivityStudentHomeBinding? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate: ActivityStudentHomeBinding = ActivityStudentHomeBinding.inflate(
            layoutInflater
        )
        binding = inflate
        setContentView(inflate.root)

        val recyclerView=binding!!.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        val db = DBHelper(this, null)
        val adapter = db.getQuizListArray()?.let { QuizListAdapterStudent(it) }
        recyclerView.adapter = adapter

    }
}