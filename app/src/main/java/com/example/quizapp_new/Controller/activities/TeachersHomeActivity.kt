package com.example.quizapp_new.Controller.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.quizapp_new.R
import com.example.quizapp_new.databinding.ActivityHomeBinding
import com.quizapp.database.DBHelper

class TeachersHomeActivity : AppCompatActivity() ,
    NavigationView.OnNavigationItemSelectedListener{

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_AddQuiz
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
        val graph = navController.navInflater.inflate(R.navigation.mobile_navigation)
        navController.graph = graph
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.nav_AddQuiz) {
            Toast.makeText(this@TeachersHomeActivity, "Add Question", Toast.LENGTH_SHORT).show()
            val edittext = EditText(this@TeachersHomeActivity)
            edittext.hint="Please Enter Question Topic"
            val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this@TeachersHomeActivity)
            alert.setMessage("Please Enter Question Topic")
            alert.setTitle("Add Question Topic")

            alert.setView(edittext)

            alert.setPositiveButton("Ok",
                DialogInterface.OnClickListener { dialog, whichButton -> //What ever you want to do with the value
                    val YouEditTextValue = edittext.text.toString()
                    val db = DBHelper(this@TeachersHomeActivity, null)
                    //Add new Question Bank
                    db.addQuiz(YouEditTextValue)
                    //After Adding question bank , add questions on that perticular question bank
                    var i= Intent(this@TeachersHomeActivity, MainActivity::class.java)
                    i.putExtra("quiz_id",db.getQuizList()?.count)
                    startActivity(i)
                })

            alert.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, whichButton ->
                    // what ever you want to do with No option.
                })

            alert.show()


        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        var i = Intent(this@TeachersHomeActivity, LandingPage::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
        finish()
    }
}