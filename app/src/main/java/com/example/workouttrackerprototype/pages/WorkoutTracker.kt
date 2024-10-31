package com.example.workouttrackerprototype.pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workouttrackerprototype.model.Exercise
import com.example.workouttrackerprototype.model.Workout
import com.example.workouttrackerprototype.ui.theme.WorkoutTrackerPrototypeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutTrackerPrototypeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    WorkOutTrackerPrototype()
                }
            }
        }
    }
}



@Composable
fun WorkOutTrackerPrototype() {
    var name by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }
    val workout = remember { Workout() }
    val exerciseList = remember { mutableStateListOf<Exercise>() }

    // State variables to control UI display
    var showRepsOnly by remember { mutableStateOf(false) }
    var showAddRepsButton by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    // Validation Error State Variables
    var nameError by remember { mutableStateOf(false) }
    var repsError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text("Add an Exercise!", style = MaterialTheme.typography.h6)

        // Conditional Dialog
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Add More Reps?") },
                text = { Text("Would you like to add more reps to the last exercise?") },
                confirmButton = {
                    Button(onClick = {
                        showRepsOnly = true
                        showAddRepsButton = true
                        showDialog = false
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        showRepsOnly = false
                        showAddRepsButton = true
                        showDialog = false
                    }) {
                        Text("No")
                    }
                }
            )
        }

        // Show fields based on `showRepsOnly`
        if (!showRepsOnly) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it
                    nameError = false} , // Reset error state on change
                label = { Text("Exercise Name") },
                isError = nameError,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            if (nameError) {
                Text("Please enter a valid exercise name", color = MaterialTheme.colors.error)
            }

            OutlinedTextField(
                value = loading,
                onValueChange = { loading = it },
                label = { Text("Loading (kg)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }

        // Reps input field (shown in both cases)
        OutlinedTextField(
            value = reps,
            onValueChange = { reps = it
                repsError = false  // Reset error state on change
            },
            label = { Text("Reps") },
            isError = repsError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        if (repsError) {
            Text("Reps must be greater than 0", color = MaterialTheme.colors.error)
        }

        // First Button - "Log New Exercise" (only shown if `showRepsOnly` is false)
        if (!showRepsOnly) {
            Button(
                onClick = {
                    // Validation checks
                    val repsInt = reps.toIntOrNull()
                    if (name.isBlank()) {
                        nameError = true
                    }
                    if (repsInt == null || repsInt <= 0) {
                        repsError = true
                    }

                    if (!nameError && !repsError) {
                        val exercise = Exercise(
                            name = name,
                            loading = loading.toIntOrNull() ?: 0,
                            reps = mutableListOf(repsInt)
                        )
                        workout.addExercise(exercise)
                        exerciseList.add(exercise)
                        name = ""
                        loading = ""
                        reps = ""

                        if (exerciseList.size >= 1) {
                            showDialog = true
                        }
                    }
                },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Log New Exercise!")
            }
        }

        // Second Button - "Add More Reps" (conditionally shown if `showAddRepsButton` is true)
        if (showAddRepsButton) {
            Button(
                onClick = {
                    val repsToAdd = reps.toIntOrNull() ?: 0
                    if (repsToAdd > 0) {
                        workout.addRepsToLastExercise(repsToAdd)
                        exerciseList[exerciseList.lastIndex] = workout.exerciseList.last()
                        reps = ""
                        showDialog = true
                    } else {
                        repsError = true
                    }
                },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Add More Reps to Last Exercise")
            }
        }


        Spacer(modifier = Modifier.height(16.dp))
        Text("Exercise Log", style = MaterialTheme.typography.h6)


        exerciseList.forEachIndexed { index, exercise ->
            Text(
                "${index + 1}. ${exercise.name} - " +
                        "${if (exercise.loading == 0) "Body weight" else "${exercise.loading}kg"} " +
                        "for ${exercise.reps.count()} ${if (exercise.reps.count() > 1) "sets" else "set"} of ${exercise.reps.joinWithAnd()} reps"
            )
        }
    }
}
// To make sure we say and between final two sets, but not before then.
fun <T>MutableList<T>.joinWithAnd(): String {
    return when (size) {
        0 -> ""
        1 -> "${this[0]}"
        2 -> "${this[0]} and ${this[1]}"
        else -> dropLast(1).joinToString(", ") + ", and ${last()}"
    }
}

