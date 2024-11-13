package com.example.gym_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gym_app.model.CoachItem
import com.example.gym_app.model.CoachItemArrayAdapter
import com.google.firebase.firestore.FirebaseFirestore

class CoachActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore

    private lateinit var filterText: TextView
    private lateinit var filterButton: ImageButton
    private lateinit var returnButton: Button
    private lateinit var createButton: Button
    private lateinit var myList: ListView
    private lateinit var coachItem: MutableList<CoachItem>
    private lateinit var adapter: CoachItemArrayAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coach);
        firestore = FirebaseFirestore.getInstance();
        filterText = findViewById(R.id.editTextTextSearch);
        filterButton = findViewById(R.id.imageButtonFilter);
        returnButton = findViewById(R.id.bottonReturn);
        createButton = findViewById(R.id.buttonAddExercise);
        myList = findViewById<ListView>(R.id.listView);
        coachItem = mutableListOf<CoachItem>();
        adapter = CoachItemArrayAdapter(this, R.layout.coach_item, coachItem);

        myList.adapter = adapter;
        loadWorkouts()

        returnButton.setOnClickListener {
            finish()
        }

        createButton.setOnClickListener {
            val intent = Intent(this, CreateWorkoutActivity::class.java)
            startActivity(intent)
        }

        filterButton.setOnClickListener {
            if (filterText.text.isNotEmpty() || filterText.text.isNotBlank()) {
                firestore.collection("workouts").whereEqualTo("level", filterText.text).get()
                    .addOnSuccessListener { result ->
                        if (!result.isEmpty) {
                            for (document in result) {
                                coachItem.add(
                                    CoachItem(
                                        document.getString(
                                            "name"
                                        ).toString(),
                                        document.id,
                                        document.getLong("level").toString(),
                                        document.getString("description").toString(),
                                        document.getString("videoUrl").toString(),
                                        document.getString("estimatedTime").toString()
                                    )
                                )
                            }
                            adapter.notifyDataSetChanged()
                        } else {
                            Toast.makeText(
                                this, "ERROR AL CARGAR WORKOUTS", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                loadWorkouts()
            }
        }
    }

    private fun loadWorkouts() {
        firestore.collection("workouts").get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    for (document in result) {
                        coachItem.add(
                            CoachItem(
                                document.id,
                                document.getString(
                                    "name"
                                ).toString(),
                                document.getLong("level").toString(),
                                document.getString("description").toString(),
                                document.getString("video_url").toString(),
                                document.getString("estimatedTime").toString()
                            )
                        )
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this, "ERROR AL CARGAR WORKOUTS", Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun refreshActivtiy() {
        recreate()
    }
}