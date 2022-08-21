package com.example.quizapp_new.models.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.quizapp.database.DBHelper
import android.content.DialogInterface

import android.widget.EditText
import com.example.quizapp_new.Controller.activities.MainActivity
import com.example.quizapp_new.databinding.FragmentHomeBinding
import com.quizapp.adapters.QuizListAdapter


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        val recyclerView=binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        val db = DBHelper(requireContext(), null)
        val adapter = db.getQuizListArray()?.let { QuizListAdapter(it) }


        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter

        binding.fab.setOnClickListener { view ->



            val edittext = EditText(context)
            edittext.hint="Please Enter Question Subject"
            val alert: AlertDialog.Builder = AlertDialog.Builder(context)
            alert.setMessage("Please Enter Question Subject")
            alert.setTitle("Add Question Subject")

            alert.setView(edittext)

            alert.setPositiveButton("Ok",
                DialogInterface.OnClickListener { dialog, whichButton -> //What ever you want to do with the value
                    val YouEditTextValue = edittext.text.toString()
                    val db = DBHelper(requireContext(), null)
                    //Add new Question topic
                    db.addQuiz(YouEditTextValue)
                    //After Adding question topic , add questions on that specific question topic
                    var i=Intent(context, MainActivity::class.java)
                    i.putExtra("quiz_id",db.getQuizList()?.count)
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(i)
                })

            alert.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, whichButton ->

                })

            alert.show()


        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}