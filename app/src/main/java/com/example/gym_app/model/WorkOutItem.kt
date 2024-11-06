package com.example.gym_app.model

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.gym_app.PlayActivity
import com.example.gym_app.R

class WorkOutItem(
    var workoutName: String,
    var workoutLevel: Int,
    var realTime: String,
    var predictedTime: String,
    var workoutDate: String,
    var exercisesDone: String,
    var videoURL: String
)

class WorkoutItemArrayAdapter(
    context: Context?,
    resource: Int,
    objects: List<WorkOutItem>?
) : ArrayAdapter<WorkOutItem>(context!!, resource, objects!!) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(this.context)
            .inflate(R.layout.workout_item, parent, false)

        val userName = view.findViewById<TextView>(R.id.textViewName)
        val userLevel = view.findViewById<TextView>(R.id.textViewLevel)
        val realTime = view.findViewById<TextView>(R.id.textRealTime)
        val predictedTime = view.findViewById<TextView>(R.id.textViewPredictedTime)
        val workoutDate = view.findViewById<TextView>(R.id.textViewWorkoutDate)
        val exercisesDone = view.findViewById<TextView>(R.id.textViewExercisesDone)
        val button = view.findViewById<TextView>(R.id.button)

        getItem(position)?.let {
            userName.text = it.workoutName
            userLevel.text = it.workoutLevel.toString()
            realTime.text = it.realTime
            predictedTime.text = it.predictedTime
            workoutDate.text = it.workoutDate
            exercisesDone.text = it.exercisesDone
        }
        button.setOnClickListener {
            val intent = Intent(context, PlayActivity::class.java)
            intent.putExtra("videoURL", getItem(position)?.videoURL)
            context.startActivity(intent)
        }
        return view
    }
}
