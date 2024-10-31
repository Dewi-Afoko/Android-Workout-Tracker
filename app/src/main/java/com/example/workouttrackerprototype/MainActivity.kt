package com.example.workouttrackerprototype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
                onValueChange = { name = it },
                label = { Text("Exercise Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

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
            onValueChange = { reps = it },
            label = { Text("Reps") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        // First Button - "Log New Exercise" (only shown if `showRepsOnly` is false)
        if (!showRepsOnly) {
            Button(
                onClick = {
                    // Create the exercise, treating an empty `loading` field as 0
                    val exercise = Exercise(
                        name = name,
                        loading = loading.toIntOrNull() ?: 0, // Default to 0 if `loading` is empty
                        reps = mutableListOf(reps.toIntOrNull())
                    )
                    workout.addExercise(exercise)
                    exerciseList.add(exercise)
                    name = ""
                    loading = ""
                    reps = ""

                    // Show the dialog if this is the first exercise added
                    if (exerciseList.size >= 1) {
                        showDialog = true
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
                    workout.addRepsToLastExercise(repsToAdd)
                    exerciseList[exerciseList.lastIndex] = workout.exerciseList.last() // Update list for UI
                    reps = ""

                    // Show dialog again after adding reps to prompt for additional reps
                    showDialog = true
                },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Add More Reps to Last Exercise")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Exercise Log", style = MaterialTheme.typography.h6)

        exerciseList.forEachIndexed { index, exercise ->
            Text("${index + 1}. ${exercise.name} - ${exercise.loading}kg for ${exercise.reps.count()} sets of ${exercise.reps.joinToString(", ")} reps")
        }
    }
}