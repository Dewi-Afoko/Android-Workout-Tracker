package com.example.workouttrackerprototype.model

class Workout {
    var exerciseList = mutableListOf<Exercise>()

    fun addExercise(exercise: Exercise) {
        this.exerciseList.add(exercise)
    }

    fun getExercise(exercisePosition: Number): Exercise? {
        if (this.exerciseList.isNotEmpty() && exercisePosition.toInt() < exerciseList.size) {
            return this.exerciseList[exercisePosition.toInt()]
        } else {
            return null
        }
    }

    fun addRepsToLastExercise(reps: Int) {
        if (this.exerciseList.isNotEmpty()) {
            val lastExercise = this.exerciseList.last()
            lastExercise.reps.add(reps)
        }
    }

    fun allExercises(): MutableList<Exercise> {
        return this.exerciseList
    }
}