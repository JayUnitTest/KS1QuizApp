package com.quizapp.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp_new.R
import com.example.quizapp_new.Controller.activities.TeachersHomeActivity
import com.example.quizapp_new.Controller.activities.TeachersQuestionsActivity
import com.example.quizapp_new.models.QuizModel
import com.quizapp.database.DBHelper

class QuizListAdapter(private val quizArray: ArrayList<QuizModel>) :
    RecyclerView.Adapter<QuizListAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.quiz_list, parent, false)



        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("Range")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val quizName = quizArray.get(position).name
        Log.e("quiz name", quizName!!)
        holder.btnQuiz.text = quizName
        holder.btnQuiz.setOnClickListener() {
//            Toast.makeText(it.context, "index $position", Toast.LENGTH_SHORT).show()
            var i = Intent(it.context, TeachersQuestionsActivity::class.java)
            i.putExtra("quiz_id", quizArray.get(position).id)
            it.context.startActivity(i)
        }

        holder.imgDelete.setOnClickListener(){
            val db = DBHelper(it.context, null)
            db.deleteQuiz(quizArray.get(position).id.toString())
            var i = Intent(it.context, TeachersHomeActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
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
        val imgDelete: ImageView = itemView.findViewById(R.id.imgDelete)
    }
}
