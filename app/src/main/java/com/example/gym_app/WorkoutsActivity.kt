package com.example.gym_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WorkoutsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workouts)

        val returnButton: Button = findViewById(R.id.returnButton)
        val profileButton: Button = findViewById(R.id.profileButton)
        val coachButton: Button = findViewById(R.id.coachButton)

        returnButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        coachButton.setOnClickListener {
            val intent = Intent(this, CoachActivity::class.java)
            startActivity(intent)
        }
    }
}