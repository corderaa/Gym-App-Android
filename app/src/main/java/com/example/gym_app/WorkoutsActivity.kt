package com.example.gym_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class WorkoutsActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workouts)

        firestore = FirebaseFirestore.getInstance()

        val returnButton: Button = findViewById(R.id.returnButton)
        val profileButton: Button = findViewById(R.id.profileButton)
        val coachButton: Button = findViewById(R.id.coachButton)
        val playButton: Button = findViewById(R.id.playButton)

        val completionDate: String
        val completionProgress: String
        val level: Int
        val name: String
        val estimatedTime: String
        val time: Int
        val videoURL: String

        var filterNumber: Int
        filterNumber = 4

        firestore.collection("workouts").whereLessThanOrEqualTo("level",filterNumber).get()
            .addOnSuccessListener{ documents ->
                if(!documents.isEmpty) {
                    for (document in documents) {

                    }
                } else {
                    Toast.makeText(this, "", Toast.LENGTH_SHORT)
                }

            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error con la base de datos", Toast.LENGTH_SHORT)
            }

        returnButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        coachButton.setOnClickListener {
            val intent = Intent(this, CoachActivity::class.java)
            startActivity(intent)
        }
        playButton.setOnClickListener {
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
        }
    }
}