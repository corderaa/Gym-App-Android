package com.example.gym_app.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.gym_app.R

class CoachItem(
    var workoutName: String,
    var workoutLevel: Long?,
    var workoutDescription: String,
    var workoutVideoUrl: String,
)

class CoachItemArrayAdapter(
    context: Context?,
    resource: Int,
    objects: List<CoachItem>?
) : ArrayAdapter<CoachItem>(context!!, resource, objects!!) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(this.context)
            .inflate(R.layout.coach_item, parent, false)

        val workoutName = view.findViewById<TextView>(R.id.textViewWorkoutName)
        val workoutLevel = view.findViewById<TextView>(R.id.textViewLevel)
        val workoutDescription = view.findViewById<TextView>(R.id.textViewWorkoutDescription)
        val workoutVideoUrl = view.findViewById<TextView>(R.id.textViewVideoUrl)

        getItem(position)?.let {
            workoutName.text = it.workoutName
            workoutLevel.text = it.workoutLevel.toString()
            workoutDescription.text = it.workoutDescription
            workoutVideoUrl.text = it.workoutVideoUrl
        }
        return view
    }
}
