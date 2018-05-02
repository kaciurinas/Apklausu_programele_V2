package com.example.rokas.apklausuprogramele.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.example.rokas.apklausuprogramele.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOpenLogin : Button = findViewById(R.id.btn_open_login_screen);
        btnOpenLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity:: class.java)
            startActivity(intent);
        }

        val btnClose : Button = findViewById(R.id.btn_exit);
        btnClose.setOnClickListener {
            finish()
        }
    }


}
