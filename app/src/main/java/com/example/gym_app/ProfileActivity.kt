package com.example.gym_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

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

        loginTextView.text = UserSesion.getUserLogin()
        gmailTextView.text = UserSesion.getUserMail()
        levelTextView.text = UserSesion.getUserLevel().toString()
        userTypeTextView.text = UserSesion.getUserAuthority()
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = Date(UserSesion.getBirthDate()!!)
        birthdayTextView.text = format.format(date)

        returnButton.setOnClickListener {
            val intent = Intent(this, WorkoutsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}