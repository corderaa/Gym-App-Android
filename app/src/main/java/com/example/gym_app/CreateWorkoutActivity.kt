package com.example.gym_app

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.gym_app.model.CoachItem
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class CreateWorkoutActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var exerciseSpinner : Spinner;
    private val myList = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        firestore = FirebaseFirestore.getInstance()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_workout)
        exerciseSpinner = findViewById(R.id.spinnerExercises)
        loadExercises()
        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.create_workout_spinner_layout, R.id.textViewExerciseName, myList)
        exerciseSpinner.adapter = adapter
    }

    private fun loadExercises() {

        firestore.collection("exercises").get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    for (document in result) {
                        Log.i(TAG, document.getString("name").toString())
                        myList.add(document.getString("name").toString())
                    }
                }
                else {
                    Toast.makeText(this, "No hay exercises", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}