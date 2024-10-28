package com.example.gym_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {

        firestore = FirebaseFirestore.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        val returnButton: Button = findViewById(R.id.buttonReturn)
        val loginTextView: TextView = findViewById(R.id.textViewLogin)
        val gmailTextView: TextView = findViewById(R.id.textViewMail)
        val levelTextView: TextView = findViewById(R.id.textViewLevel)
        val userTypeTextView: TextView = findViewById(R.id.textViewAuthority)
        val birthdayTextView: TextView = findViewById(R.id.textViewBirthDate)

        try {

            loginTextView.text = "${intent.getStringExtra("LOGIN")}"
            gmailTextView.text = "${intent.getStringExtra("EMAIL")}"
            levelTextView.text = "${intent.getIntExtra("LEVEL", 0)}"
            userTypeTextView.text = "${intent.getStringExtra("AUTHORITY")}"
            birthdayTextView.text = "${intent.getStringExtra("BIRTHDAY")}"

        } catch (e: Exception) {
            loginTextView.text = "Login: Not available"
        }

        returnButton.setOnClickListener {
            val intent = Intent(this, WorkoutsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}