package com.example.rokas.apklausuprogramele.network

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.rokas.apklausuprogramele.R.id.surveyListSpinner
import com.example.rokas.apklausuprogramele.network.models.Answer
import com.example.rokas.apklausuprogramele.network.models.Question
import com.example.rokas.apklausuprogramele.network.models.Questionaire
import org.json.JSONException
import org.json.JSONObject

object ApiClient {

    fun getQuestions(context: Context,
                     id: Int,
                     callback: (List<Question>) -> Unit,
                     error: (String) -> Unit) {
        val url = "http://kaciurinas.lt/WebApi/v1/?op=getquestions&id=$id"
        val questionsList = mutableListOf<Question>()
        val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String> { s ->
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
                            callback(questionsList)
                        } else {
                            val errorString = obj.getString("message")
                            error(errorString)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { volleyError -> error(volleyError.message ?: "") })

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add<String>(stringRequest)
    }

    fun loadAnswers(context: Context,
                            question_id: Int,
                            callback: (List<Answer>) -> Unit,
                            error: (String) -> Unit) {
        val url_answers =  "http://kaciurinas.lt/WebApi/v1/?op=getanswers&id=$question_id"
        val answerList = mutableListOf<Answer>()
        VolleyLog.DEBUG = true
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
                            callback(answerList)
                        } else {
                            error(obj.getString("message"))
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { volleyError -> error(volleyError.message ?: "") })

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add<String>(stringRequest)
    }

    fun loadSurveys(context: Context,
                    callback: (List<Questionaire>) -> Unit,
                    error: (String) -> Unit) {
        VolleyLog.DEBUG = true;
        val surveyList = mutableListOf<Questionaire>()
        val stringRequest = StringRequest(Request.Method.GET,
                EndPoints.URL_GET_SURVEYS,
                Response.Listener<String> { s ->
                    try {
                        val obj = JSONObject(s)
                        if (!obj.getBoolean("error")) {
                            val array = obj.getJSONArray("surveys")
                            for (i in 0..array.length() - 1) {
                                val name = array.getJSONObject(i).getString("name")
                                val id = array.getJSONObject(i).getString("id").toInt()
                                val nof_questions = array.getJSONObject(i).getString("nof_questions").toInt()
                                val questionaire = Questionaire(
                                        id, name, nof_questions
                                )
                                surveyList.add(questionaire);
                            }
                            callback(surveyList);
                        } else {
                            error(obj.getString("message"))
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { volleyError -> error(volleyError.message ?: "") })

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add<String>(stringRequest)
    }

}