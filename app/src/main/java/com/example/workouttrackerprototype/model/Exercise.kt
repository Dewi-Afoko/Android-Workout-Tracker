package com.example.workouttrackerprototype.model

data class Exercise(val name: String, val loading: Number, val reps: MutableList<Int?> = mutableListOf())
