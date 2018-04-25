package com.example.rokas.apklausuprogramele

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_login.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.rokas.apklausuprogramele.R.id.surveyListSpinner
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button4.setOnClickListener {
            val username_input = editText.text.toString().trim();
            val password_input = editText2.text.toString().trim();
            if(username_input.isNullOrBlank() || password_input.isNullOrBlank()) {
                Toast.makeText(applicationContext, "Laukai negali būti tušti!", Toast.LENGTH_SHORT).show()

            }
            else{
                if(username_input.equals("root") && password_input.equals("root")){
                    Toast.makeText(applicationContext, "All good", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ConfigureActivity :: class.java)
                    startActivity(intent);
                }
            }
        }
    }
}