package com.example.gym_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ListView
import android.widget.TextView
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

        firestore = FirebaseFirestore.getInstance()

        val returnButton: Button = findViewById(R.id.returnButton)
        val profileButton: Button = findViewById(R.id.profileButton)
        val coachButton: Button = findViewById(R.id.coachButton)
        val filterButton: ImageButton = findViewById(R.id.imageButtonFilter4)
        val editTextSearch: TextView = findViewById(R.id.editTextTextSearch4)

        var date: String?
        var completionProgress: String?
        var level: Int?
        var name: String?
        var estimatedTime: String?
        var time: String?
        var videoURL: String?

        if (UserSesion.getUserAuthority() != null && UserSesion.getUserAuthority() != "Entrenador")
            coachButton.visibility = View.INVISIBLE

        firestore.collection("users").whereEqualTo("login", login).get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    if(login!=null) {
                        for(document in result) {
                            document.reference.collection("history").get()
                                .addOnSuccessListener { historyResult ->
                                    if (!historyResult.isEmpty){
                                        for(documentHistory in historyResult){
                                            name = documentHistory.getString("name")
                                            date = documentHistory.getString("date")
                                            completionProgress = documentHistory.getString("completionProgress")
                                            level = documentHistory.getLong("level")?.toInt()
                                            estimatedTime = documentHistory.getString("estimatedTime")
                                            time = documentHistory.getString("time")
                                            videoURL = documentHistory.getString("videoURL")
                                            if(name != null && level != null && time != null && estimatedTime != null && date != null && completionProgress != null && videoURL != null) {
                                                workoutItem.add(
                                                    WorkOutItem(
                                                        name!!,
                                                        level!!,
                                                        time!!,
                                                        estimatedTime!!,
                                                        date!!,
                                                        completionProgress!!,
                                                        videoURL!!
                                                    )
                                                )
                                            }
                                        }
                                        val adapter = WorkoutItemArrayAdapter(this, R.layout.workout_item, workoutItem)
                                        myList.adapter = adapter
                                    }
                                }
                        }
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
        filterButton.setOnClickListener {
            if (!editTextSearch.text.isEmpty()) {
                workoutItem.clear()
                val historyLevel: Int
                historyLevel = editTextSearch.text.toString().toInt()
                firestore.collection("users").whereEqualTo("login", login).get()
                    .addOnSuccessListener { result ->
                        if (!result.isEmpty) {
                            if (login != null) {
                                for (document in result) {
                                    document.reference.collection("history")
                                        .whereEqualTo("level", historyLevel).get()
                                        .addOnSuccessListener { historyResult ->
                                            if (!historyResult.isEmpty) {
                                                for (documentHistory in historyResult) {
                                                    name = documentHistory.getString("name")
                                                    date = documentHistory.getString("date")
                                                    completionProgress =
                                                        documentHistory.getString("completionProgress")
                                                    level =
                                                        documentHistory.getLong("level")?.toInt()
                                                    estimatedTime =
                                                        documentHistory.getString("estimatedTime")
                                                    time = documentHistory.getString("time")
                                                    videoURL = documentHistory.getString("videoURL")
                                                    if (name != null && level != null && time != null && estimatedTime != null && date != null && completionProgress != null && videoURL != null) {
                                                        workoutItem.add(
                                                            WorkOutItem(
                                                                name!!,
                                                                level!!,
                                                                time!!,
                                                                estimatedTime!!,
                                                                date!!,
                                                                completionProgress!!,
                                                                videoURL!!
                                                            )
                                                        )
                                                    }
                                                }
                                                val adapter = WorkoutItemArrayAdapter(
                                                    this,
                                                    R.layout.workout_item,
                                                    workoutItem
                                                )
                                                myList.adapter = adapter
                                            }
                                        }
                                }
                            } else {
                                Log.i("usersElse", "error")
                            }
                        } else {
                            Toast.makeText(this, "Histórico vacío", Toast.LENGTH_SHORT)
                        }

                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Error con la base de datos", Toast.LENGTH_SHORT)
                    }
                val adapter = WorkoutItemArrayAdapter(this, R.layout.workout_item, workoutItem)
                myList.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }
}