package com.quizapp.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp_new.R
import com.example.quizapp_new.models.QuestionsModel
import com.quizapp.database.DBHelper

class QuestionsListTeachersAdapter(private val quizArray: ArrayList<QuestionsModel>) :
    RecyclerView.Adapter<QuestionsListTeachersAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.questions_list_teachers, parent, false)



        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("Range")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val quizName = quizArray.get(position).name
        Log.e("quiz name", quizName!!)
        holder.btnQuiz.text = quizName 

        holder.txtAnswers.text = """
               A. ${quizArray[position].option1}
               B. ${quizArray[position].option2}
               C. ${quizArray[position].option3}
               D. ${quizArray[position].option4}
               """.trimIndent()
        "Answer. " + quizArray[position].answer

        holder.imgDelete.setOnClickListener(){
            val db = DBHelper(it.context, null)
            db.deleteQuestion(quizArray[position].id.toString(), it.context)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return quizArray.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val btnQuiz: TextView = itemView.findViewById(R.id.btnQuiz)
        val imgDelete: ImageView = itemView.findViewById(R.id.imgDelete)
        val txtAnswers: TextView = itemView.findViewById(R.id.txtAnswers)
    }
}
