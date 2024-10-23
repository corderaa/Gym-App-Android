package com.example.gym_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val registerButton: Button = findViewById(R.id.registerButton)
        val goingToMenu: Button = findViewById(R.id.menu)
        val rememberMe: CheckBox = findViewById(R.id.rememberMe)
        val userText: EditText = findViewById(R.id.userText)
        val passwordText: EditText = findViewById(R.id.passwordText)
        val sharedP: SharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)

        userText.setText(sharedP.getString("user", ""))
        passwordText.setText(sharedP.getString("password", ""))
        if (userText.text.isEmpty() && passwordText.text.isEmpty()) {
            rememberMe.isChecked = false
        } else {
            rememberMe.isChecked = true
        }
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        goingToMenu.setOnClickListener {
            val intent = Intent(this, WorkoutsActivity::class.java)
            startActivity(intent)
            if(rememberMe.isChecked){
                val editor: SharedPreferences.Editor = sharedP.edit()
                editor.putString("user", userText.text.toString().trim())
                editor.putString("password", passwordText.text.toString().trim())
                editor.apply()
            } else {
                val editor: SharedPreferences.Editor = sharedP.edit()
                editor.remove("user")
                editor.remove("password")
                editor.apply()
            }
        }
    }
}