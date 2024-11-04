package com.example.gym_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.example.gym_app.model.WorkOutItem
import com.example.gym_app.model.WorkoutItemArrayAdapter

class WorkoutsActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workouts)

        val myList = findViewById<ListView>(R.id.listView)
        val workoutItem = mutableListOf<WorkOutItem>()
        val login = intent.getStringExtra("login")

        // DumMy items
        workoutItem.add(WorkOutItem("Push-ups", 10, "20 minutes", "14 minutes", "29 Enero", "50%"))
        workoutItem.add(WorkOutItem("Push-ups", 10, "20 minutes", "14 minutes", "29 Enero", "50%"))


        val adapter = WorkoutItemArrayAdapter(this, R.layout.workout_item, workoutItem)
        myList.adapter = adapter

        firestore = FirebaseFirestore.getInstance()

        val returnButton: Button = findViewById(R.id.returnButton)
        val profileButton: Button = findViewById(R.id.profileButton)
        val coachButton: Button = findViewById(R.id.coachButton)

        val date: String
        val completionProgress: String
        val level: Int
        var name: String
        val estimatedTime: String
        val time: Int
        val videoURL: String

        val docRef = firestore.collection("users").whereEqualTo("login", login).get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    if(login!=null) {
                        Log.i("users", login)
                        Log.i("result", result.toString())
                    }else {
                        Log.i("usersElse", "error")
                    }
                } else {
                    Toast.makeText(this, "Histórico vacío", Toast.LENGTH_SHORT)
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
            intent.putExtra("login", login)
            startActivity(intent)
        }
        coachButton.setOnClickListener {
            val intent = Intent(this, CoachActivity::class.java)
            startActivity(intent)
        }
    }
}