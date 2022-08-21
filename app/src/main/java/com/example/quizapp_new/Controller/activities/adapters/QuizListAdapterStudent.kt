package com.quizapp.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp_new.R
import com.example.quizapp_new.Controller.activities.StartQuizActivity
import com.example.quizapp_new.models.QuizModel

class QuizListAdapterStudent(private val quizArray:ArrayList<QuizModel>) : RecyclerView.Adapter<QuizListAdapterStudent.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.quiz_list_student, parent, false)



        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("Range")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val quizName = quizArray.get(position).name
        Log.e("quiz name", quizName!!)
        holder.btnQuiz.text=quizName
        holder.btnQuiz.setOnClickListener(){
//            Toast.makeText(it.context, "index $position", Toast.LENGTH_SHORT).show()
            var i= Intent(it.context, StartQuizActivity::class.java)
            i.putExtra("quiz_id",quizArray.get(position).id)
            it.context.startActivity(i)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return quizArray.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val btnQuiz: Button = itemView.findViewById(R.id.btnQuiz)
    }
}
