package com.example.gym_app.model

data class Workouts(
    val completionDate: String,
    val completionProgress: String,
    val level: Int,
    val name: String,
    val estimatedTime: String,
    val time: Int,
    val videoURL: String
)
