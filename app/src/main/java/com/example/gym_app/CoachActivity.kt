package com.example.gym_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gym_app.model.CoachItem
import com.example.gym_app.model.CoachItemArrayAdapter
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class CoachActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.coach)
        firestore = FirebaseFirestore.getInstance()
        val filterText = findViewById<TextView>(R.id.editTextTextSearch)
        val returnButton: Button = findViewById(R.id.bottonReturn)
        val createButton: Button = findViewById(R.id.buttonAdd)
        val myList = findViewById<ListView>(R.id.listView)
        val coachItem = mutableListOf<CoachItem>()
        val adapter = CoachItemArrayAdapter(this, R.layout.coach_item, coachItem)
        myList.adapter = adapter

        if(filterText.text.isEmpty() || filterText.text.isBlank()) {
            firestore.collection("workouts").get()
                .addOnSuccessListener { result ->
                    if (!result.isEmpty) {
                        for (document in result) {
                            coachItem.add(
                                CoachItem(
                                    document.getString(
                                        "name"
                                    ).toString(),
                                    document.getLong("level").toString(),
                                    document.getString("description").toString(),
                                    document.getString("video_url").toString()
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
        }else{
            firestore.collection("workouts").whereEqualTo("level", filterText.text).get()
                .addOnSuccessListener { result ->
                    if (!result.isEmpty) {
                        for (document in result) {
                            coachItem.add(
                                CoachItem(
                                    document.getString(
                                        "name"
                                    ).toString(),
                                    document.getLong("level").toString(),
                                    document.getString("description").toString(),
                                    document.getString("video_url").toString()
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

        returnButton.setOnClickListener {
            val intent = Intent(this, WorkoutsActivity::class.java)
            startActivity(intent)
        }

        createButton.setOnClickListener{
            val intent = Intent(this, CreateWorkoutActivity::class.java)
            startActivity(intent)
        }
    }
}