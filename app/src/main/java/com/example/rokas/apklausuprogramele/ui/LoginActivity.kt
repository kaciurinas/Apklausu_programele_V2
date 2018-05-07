package com.example.rokas.apklausuprogramele.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.rokas.apklausuprogramele.R
import kotlinx.android.synthetic.main.activity_login.*

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
                    val intent = Intent(this, ConfigureActivity:: class.java)
                    startActivity(intent);
                }
            }
        }
    }
}