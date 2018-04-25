package com.example.rokas.apklausuprogramele

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_configure.*
import org.json.JSONException
import org.json.JSONObject

class SurveyActivity : AppCompatActivity() {


    var questionsList = ArrayList<Question>()
    var answerList = ArrayList<Answer>()

    lateinit var surveyNrText: TextView
    lateinit var answer1: Button
    lateinit var answer2: Button
    lateinit var answer3: Button
    lateinit var answer4: Button
    lateinit var answer5: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        val IntentObject: Intent = intent
        val activeSurvey = intent.getStringExtra("activeSurvey")
        surveyNrText= findViewById(R.id.surveyNr)
        answer1 = findViewById(R.id.answer1)
        answer2 = findViewById(R.id.answer2)
        answer3 = findViewById(R.id.answer3)
        answer4 = findViewById(R.id.answer4)
        answer5 = findViewById(R.id.answer5)

        surveyNrText.setText(activeSurvey)
        val questionsList1 = ArrayList<Question>()
        loadQuestions()


        //var questionList = getQuestions();
        for (i in 0 until questionsList1.size - 1) {
            surveyNrText.setText(questionsList.get(i).text)
            answerList.clear()
            loadAnswers(questionsList[i].id)
            when (questionsList[i].nof_answers) {
                1 -> {
                    answer1.isEnabled = true
                    answer1.setText(answerList[0].text)
                }

            }
        }
    }

    val id = "24"
    val url_questions = "http://kaciurinas.lt/WebApi/v1/?op=getquestions&id=$id"
    private fun loadQuestions() {
        VolleyLog.DEBUG = true;
        val stringRequest = StringRequest(Request.Method.GET,
                url_questions,
                Response.Listener<String> { s ->
                    try {
                        val obj = JSONObject(s)
                        if (!obj.getBoolean("error")) {
                            val array = obj.getJSONArray("questions")
                            for (i in 0..array.length() - 1) {
                                val id = array.getJSONObject(i).getString("id").toInt()
                                val text = array.getJSONObject(i).getString("text")
                                val nof_answers = array.getJSONObject(i).getString("nof_answers").toInt()
                                val question = Question(
                                        id, text, nof_answers
                                )
                                questionsList.add(question)
                            }
                            var questionIndex = 0;
                            for (i in 0..questionsList.size -1){
                                bind(questionsList.get(i), questionIndex)
                                questionIndex++
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)
    }


    private fun loadAnswers(question_id : Int) {
        val url_answers =  "http://kaciurinas.lt/WebApi/v1/?op=getanswers&id=$question_id"
        VolleyLog.DEBUG = true;
        val stringRequest = StringRequest(Request.Method.GET,
                url_answers,
                Response.Listener<String> { s ->
                    try {
                        val obj = JSONObject(s)
                        if (!obj.getBoolean("error")) {
                            val array = obj.getJSONArray("answers")
                            for (i in 0..array.length() - 1) {
                                val id = array.getJSONObject(i).getString("id").toInt()
                                val text = array.getJSONObject(i).getString("text")
                                val answer = Answer(
                                        id, text
                                )
                                answerList.add(answer)
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)
    }

    fun bind(question : Question, questionIndex : Int) {
        surveyNrText.setText(questionsList.get(1).text)
        loadAnswers(question.id)
        when (question.nof_answers) {
            1 -> {
                answer1.isEnabled = true
                answer1.setText(answerList[0].text)
            }
        }

    }
}


