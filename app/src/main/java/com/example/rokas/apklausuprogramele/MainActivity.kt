package com.example.rokas.apklausuprogramele

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOpenLogin : Button = findViewById(R.id.btn_open_login_screen);
        btnOpenLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity :: class.java)
            startActivity(intent);
        }

        val btnClose : Button = findViewById(R.id.btn_exit);
        btnClose.setOnClickListener {
            finish()
        }
    }


}
