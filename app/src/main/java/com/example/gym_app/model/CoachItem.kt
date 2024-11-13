package com.example.gym_app.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.gym_app.CoachActivity
import com.example.gym_app.R
import com.google.firebase.firestore.FirebaseFirestore

class CoachItem(
    var workoutId: String,
    var workoutName: String,
    var workoutLevel: String,
    var workoutDescription: String,
    var workoutVideoUrl: String,
    var workoutTime: String
)

class CoachItemArrayAdapter(
    context: Context?,
    resource: Int,
    objects: List<CoachItem>?
) : ArrayAdapter<CoachItem>(context!!, resource, objects!!) {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var workoutId: String
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(this.context)
            .inflate(R.layout.coach_item, parent, false)
        firestore = FirebaseFirestore.getInstance()

        val workoutName = view.findViewById<TextView>(R.id.textViewWorkoutName)
        workoutName.isEnabled = false
        val workoutLevel = view.findViewById<TextView>(R.id.textViewLevel)
        workoutLevel.isEnabled = false
        val workoutDescription = view.findViewById<TextView>(R.id.textViewWorkoutDescription)
        workoutDescription.isEnabled = false
        val workoutVideoUrl = view.findViewById<TextView>(R.id.textViewVideoUrl)
        workoutVideoUrl.isEnabled = false
        val workoutTime = view.findViewById<TextView>(R.id.textViewTiempo)
        workoutTime.isEnabled = false
        val deleteButton = view.findViewById<Button>(R.id.buttonDeleteWorkout)
        val editButton = view.findViewById<Button>(R.id.editButton)
        var isEditing = false

        getItem(position)?.let {
            workoutId = it.workoutId
            workoutName.text = it.workoutName
            workoutLevel.text = it.workoutLevel
            workoutDescription.text = it.workoutDescription
            workoutVideoUrl.text = it.workoutVideoUrl
            workoutTime.text = it.workoutTime
        }

        editButton.setOnClickListener {
            if (!isEditing) {
                workoutName.isEnabled = true
                workoutLevel.isEnabled = true
                workoutDescription.isEnabled = true
                workoutVideoUrl.isEnabled = true
                workoutTime.isEnabled = true
                deleteButton.isEnabled = true
                editButton.text = "Guardar"
                deleteButton.isEnabled = false;
                isEditing = true
            } else {
                saveWorkout(
                    workoutId,
                    workoutDescription.text.toString(),
                    workoutName.text.toString(),
                    workoutVideoUrl.text.toString(),
                    workoutLevel.text.toString(),
                    workoutTime.text.toString()
                )
                workoutName.isEnabled = false
                workoutLevel.isEnabled = false
                workoutDescription.isEnabled = false
                workoutVideoUrl.isEnabled = false
                deleteButton.isEnabled = true
                editButton.text = "Editar"
                isEditing = false
            }
        }

        deleteButton.setOnClickListener {
            deleteWorkout(workoutId, true)
        }

        return view
    }

    fun deleteWorkout(workoutId: String, msg: Boolean) {

        firestore.collection("workouts").document(workoutId).delete()
            .addOnSuccessListener {
                // Document successfully deleted
                Toast.makeText(
                    context,
                    "Se ha eliminado el workout correctamente",
                    Toast.LENGTH_SHORT
                ).show()
                (context as CoachActivity).refreshActivtiy()
            }
            .addOnFailureListener { e ->
                // Error deleting document
                if (msg) {
                    Toast.makeText(
                        context,
                        "Error al eliminar el workout: $e",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun saveWorkout(
        workoutId: String,
        workoutDescription: String,
        workoutName: String,
        workoutVideoUrl: String,
        workoutLevel: String,
        workoutTime: String
    ) {
        deleteWorkout(workoutId, false)
        val exercisesList = ArrayList<String>()
        val newWorkout = hashMapOf(
            "description" to workoutDescription,
            "estimatedTime" to workoutTime,
            "level" to Integer.parseInt(workoutLevel),
            "name" to workoutName,
            "videoUrl" to workoutVideoUrl,
            "exercises" to exercisesList
        )

        firestore.collection("workouts").add(newWorkout).addOnSuccessListener {
            Toast.makeText(
                context, "workout guardado con exito", Toast.LENGTH_SHORT
            ).show()
            (context as CoachActivity).refreshActivtiy()
        }.addOnFailureListener { e ->
            Toast.makeText(
                context,
                "Error al registrar el workout: $e",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}



