package com.example.gym_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class CreateWorkoutActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        firestore = FirebaseFirestore.getInstance()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_workout)
        val editTextTextCreateName: EditText = findViewById(R.id.editTextTextCreateName)
        val editTextCreateLevel: EditText = findViewById(R.id.editTextCreateLevel)
        val editTextCreateTime: EditText = findViewById(R.id.editTextCreateTime)
        val editTextTextCreateVideoUrl: EditText = findViewById(R.id.editTextTextCreateVideoUrl)
        val editTextTextCreateDescription: EditText =
            findViewById(R.id.editTextTextCreateDescription)
        val buttonCreate: TextView = findViewById(R.id.buttonCreate)
        val buttonExit: Button = findViewById(R.id.bottonCreateReturn)

        buttonExit.setOnClickListener{
            finish()
        }

        buttonCreate.setOnClickListener {
            if (editTextTextCreateName.text.toString()
                    .isNotEmpty() || editTextTextCreateName.text.toString()
                    .isNotBlank() || editTextCreateTime.text.toString()
                    .isNotEmpty() || editTextCreateTime.text.toString()
                    .isNotBlank() || editTextCreateLevel.text.toString()
                    .isNotEmpty() || editTextCreateLevel.text.toString()
                    .isNotBlank() || editTextTextCreateVideoUrl.text.toString()
                    .isNotEmpty() || editTextTextCreateVideoUrl.text.toString().isNotBlank()
            ) {
                val exercisesList = ArrayList<String>()
                val newWorkout = hashMapOf(
                    "description" to editTextTextCreateDescription.text.toString(),
                    "estimatedTime" to editTextCreateTime.text.toString(),
                    "level" to Integer.parseInt(editTextCreateLevel.text.toString()),
                    "name" to editTextTextCreateName.text.toString(),
                    "videoUrl" to editTextTextCreateVideoUrl.text.toString(),
                    "exercises" to exercisesList
                )

                firestore.collection("workouts").add(newWorkout).addOnSuccessListener {
                    Toast.makeText(
                        this, "workout guardado con exito", Toast.LENGTH_SHORT
                    ).show()
                    (this as CoachActivity).refreshActivtiy()
                    finish()
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "Error al registrar el workout: $e",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}