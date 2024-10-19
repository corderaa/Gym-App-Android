package com.example.gym_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        val returnButton: Button = findViewById(R.id.buttonReturn)

        returnButton.setOnClickListener {
            val intent = Intent(this, WorkoutsActivity::class.java)
            startActivity(intent)
        }
    }
}