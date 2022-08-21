package com.quizapp.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.quizapp_new.models.Teacher
import com.example.quizapp_new.Controller.activities.TeachersQuestionsActivity
import com.example.quizapp_new.models.QuestionsModel
import com.example.quizapp_new.models.QuizModel

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + Question_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                IDQuestionBank_COL + " TEXT," +
                Question_COl + " TEXT," +
                Opt1_COL + " TEXT," +
                Opt2_COL + " TEXT," +
                Opt3_COL + " TEXT," +
                Opt4_COL + " TEXT," +
                Answer_COL + " TEXT" + ")")
        val queryCreateQuiz = ("CREATE TABLE " + Quiz_TABLE_NAME + " ("
                + IDQuestionBank_COL + " INTEGER PRIMARY KEY, " +
                QuizName + " TEXT" + ")")

        val sqlCreateStatement: String = ("CREATE TABLE" + Teachers_Table_Name + " (" + Column_ID + " INTEGER PRIMARY KEY AUTO INCREMENT, "
                + Column_UserName + " TEXT NOT NULL UNIQUE, " + Column_Password + "TEXT NOT NULL)")
        // we are calling sqlite
        // method for executing our query
        db.execSQL(queryCreateQuiz)
        db.execSQL(query)
        db.execSQL(sqlCreateStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + Question_TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " + Quiz_TABLE_NAME)
        onCreate(db)
    }



    @SuppressLint("Range")
    fun getQuizListArray(): ArrayList<QuizModel>{
        val db = this.readableDatabase
        var c=db.rawQuery("SELECT * FROM " + Quiz_TABLE_NAME, null)
        val mArrayList = ArrayList<QuizModel>()
        var quizModel:QuizModel
        c.moveToFirst()
        while (!c.isAfterLast()) {
            var id=c.getInt(c.getColumnIndex(DBHelper.IDQuestionBank_COL))
            var n=c.getString(c.getColumnIndex(DBHelper.QuizName))
            quizModel= QuizModel(id,n)
            mArrayList.add(quizModel) //add the item
            c.moveToNext()
        }
        return mArrayList
    }



    // method is for adding data in our database
    fun addQuestions(
        questionBankID: String,
        question: String,
        opt1: String,
        opt2: String,
        opt3: String,
        opt4: String,
        answer: String,
    ) {
        // below we are creating
        // a content values variable
        val values = ContentValues()
        // we are inserting our values
        // in the form of key-value pair
        values.put(IDQuestionBank_COL, questionBankID)
        values.put(Question_COl, question)
        values.put(Opt1_COL, opt1)
        values.put(Opt2_COL, opt2)
        values.put(Opt3_COL, opt3)
        values.put(Opt4_COL, opt4)
        values.put(Answer_COL, answer)
        // here we are creating a writable variable of
        // our database as we want to insert values to database
        val db = this.writableDatabase
        //values are inserted into database
        db.insert(Question_TABLE_NAME, null, values)

        // closing our database
        db.close()
    }

    fun addQuiz(
        quiz_name: String,
    ) {
        val values = ContentValues()
       // values.put(IDQuestionBank_COL, questionBankID)
        values.put(QuizName, quiz_name)
        val db = this.writableDatabase
        db.insert(Quiz_TABLE_NAME, null, values)
        db.close()
    }

    // method to get
    // all data from our database
    fun getQuestionList(): Cursor? {

        //creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + Question_TABLE_NAME, null)

    }
@SuppressLint("Range")
fun getQuestionListArray(quizid: Int): ArrayList<QuestionsModel> {
        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

    val mArrayList = ArrayList<QuestionsModel>()
    var quizModel:QuestionsModel
   var c=db.rawQuery("SELECT * FROM " + Question_TABLE_NAME, null)
    c.moveToFirst()
    while (!c.isAfterLast()) {
        var id=c.getInt(c.getColumnIndex(DBHelper.IDQuestionBank_COL))
        if (quizid==id) {
            var n = c.getString(c.getColumnIndex(DBHelper.Question_COl))
            var opt1 = c.getString(c.getColumnIndex(DBHelper.Opt1_COL))
            var opt2 = c.getString(c.getColumnIndex(DBHelper.Opt2_COL))
            var opt3 = c.getString(c.getColumnIndex(DBHelper.Opt3_COL))
            var opt4 = c.getString(c.getColumnIndex(DBHelper.Opt4_COL))
            var answer = c.getString(c.getColumnIndex(DBHelper.Answer_COL))
            Log.e("questions list", opt1)
            quizModel = QuestionsModel(c.getInt(c.getColumnIndex(DBHelper.ID_COL)), n, opt1, opt2, opt3, opt4, answer)
            mArrayList.add(quizModel) //add the item
        }
        c.moveToNext()

    }

    // below code returns a cursor to
        // read data from the database

        return mArrayList
    }

    fun getQuizList(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + Quiz_TABLE_NAME, null)
    }
    fun deleteQuestion(questionID: String, ctx: Context) {

        // a variable to write our database.
        val db = this.writableDatabase

        // on below line we are calling a method to delete our
        // topic and we are comparing it with our topic name.
        db.delete(Question_TABLE_NAME, "$ID_COL=?", arrayOf(questionID))
        db.close()

        var id = TeachersQuestionsActivity.quizid
        var i = Intent(ctx, TeachersQuestionsActivity::class.java)
        i.putExtra("quiz_id", id)
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        ctx.startActivity(i)

    }


    fun deleteQuiz(quizid: String) {

        // variable to write our database.
        val db = this.writableDatabase

        // calling a method to delete topic  and we are comparing it with our topic name.
        db.delete(Quiz_TABLE_NAME, "$IDQuestionBank_COL=?", arrayOf(quizid))
        db.delete(Question_TABLE_NAME, "$IDQuestionBank_COL=?", arrayOf(quizid))
        db.close()
    }

    private fun checkUserName(teacher: Teacher): Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val userName = teacher.Username.lowercase()

        val sqlStatement = "SELECT * FROM $Teachers_Table_Name WHERE $Column_UserName = ?"
        val param = arrayOf(userName)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)

        if(cursor.moveToFirst()){
            // The user is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return -3 // error the user name is already exist
        }

        cursor.close()
        db.close()
        return 0 //User not found

    }


    fun getTeacher(teacher: Teacher) : Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val userName = teacher.Username.lowercase()
        val userPassword = teacher.Password
        //val sqlStatement = "SELECT * FROM $TableName WHERE $Column_UserName = $userName AND $Column_Password = $userPassword"

        val sqlStatement = "SELECT * FROM $Teachers_Table_Name WHERE $Column_UserName = ? AND $Column_Password = ?"
        val param = arrayOf(userName,userPassword)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)
        if(cursor.moveToFirst()){
            // The user is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }

        cursor.close()
        db.close()
        return -1 //User not found

    }

    companion object {
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "Quiz App"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val Question_TABLE_NAME = "QuestionsTable"
        val Quiz_TABLE_NAME = "QuizTable"

        // below is the variable for id column
        val ID_COL = "id"
        val IDQuestionBank_COL = "id_ques_bank"

        // below is the variable for name column
        val Question_COl = "question"

        // below is the variable for ans column
        val Opt1_COL = "option1"
        val Opt2_COL = "option2"
        val Opt3_COL = "option3"
        val Opt4_COL = "option4"
        val Answer_COL = "answer"
        val QuizName = "quiz_name"

        /* Teacher Table */
        val Teachers_Table_Name = "Teachers"
        val Column_ID = "ID"
        val Column_UserName = "UserName"
        val Column_Password = "Password"
        /*************************/



    }
}
