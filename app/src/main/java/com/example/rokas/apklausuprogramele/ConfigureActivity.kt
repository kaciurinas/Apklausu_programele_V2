package com.example.rokas.apklausuprogramele

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.rokas.apklausuprogramele.R.id.surveyListSpinner
import kotlinx.android.synthetic.main.activity_configure.*
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject

class ConfigureActivity : AppCompatActivity() {

    private var questionaireList = mutableListOf<Questionaire>()
    private var nameList = arrayListOf<String>()
    var selectedQuestionaire = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configure)
        nameList = arrayListOf<String>()
        loadSurveys()
        surveyListSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedQuestionaire = surveyListSpinner.selectedItemPosition + 1;
                Toast.makeText(this@ConfigureActivity, selectedQuestionaire.toString(), Toast.LENGTH_LONG).show()

            }
        }
        val submitButton: Button = findViewById(R.id.buttonSubmit)
        submitButton.setOnClickListener {
            val intent = Intent(this, SurveyActivity :: class.java)
            intent.putExtra("activeSurvey", selectedQuestionaire.toString())
            startActivity(intent);
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
                            val array = obj.getJSONArray("surveys")
                            for (i in 0..array.length() - 1) {
                                val name = array.getJSONObject(i).getString("name")
                                val id = array.getJSONObject(i).getString("id").toInt()
                                val nof_questions = array.getJSONObject(i).getString("nof_questions").toInt()
                                nameList.add(name);
                                val questionaire = Questionaire(
                                        id, name, nof_questions
                                )
                                questionaireList.add(questionaire);
                                surveyListSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nameList)

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
}
