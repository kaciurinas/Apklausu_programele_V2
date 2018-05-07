package com.example.rokas.apklausuprogramele.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.rokas.apklausuprogramele.R
import com.example.rokas.apklausuprogramele.network.EndPoints
import com.example.rokas.apklausuprogramele.network.models.Questionaire
import kotlinx.android.synthetic.main.activity_configure.*
import org.json.JSONException
import org.json.JSONObject



class ConfigureActivity : AppCompatActivity() {

    private var selectedQuestionaire: Questionaire? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configure)
        loadSurveys()
        surveyListSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedQuestionaire = surveyListSpinner.adapter.getItem(p2) as Questionaire
                Toast.makeText(this@ConfigureActivity, selectedQuestionaire.toString(), Toast.LENGTH_LONG).show()

            }
        }
        val submitButton: Button = findViewById(R.id.buttonSubmit)
        submitButton.setOnClickListener {
            if (selectedQuestionaire != null) {
                SurveyActivity.start(this, selectedQuestionaire!!)
            } else {
                Toast.makeText(this, "Select a goddamn survey, fool!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loadSurveys(){
        VolleyLog.DEBUG = true;
        val stringRequest = StringRequest(Request.Method.GET,
                EndPoints.URL_GET_SURVEYS,
                Response.Listener<String> { s ->
                    try {
                        val obj = JSONObject(s)
                        if (!obj.getBoolean("error")) {
                            val questionaireList = mutableListOf<Questionaire>()
                            val array = obj.getJSONArray("surveys")
                            for (i in 0..array.length() - 1) {
                                val name = array.getJSONObject(i).getString("name")
                                val id = array.getJSONObject(i).getString("id").toInt()
                                val nof_questions = array.getJSONObject(i).getString("nof_questions").toInt()
                                val questionaire = Questionaire(
                                        id, name, nof_questions
                                )
                                questionaireList.add(questionaire)
                            }
                            surveyListSpinner.adapter = ArrayAdapter<Questionaire>(
                                    this,
                                    android.R.layout.simple_spinner_item,
                                    questionaireList
                            )
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
}
