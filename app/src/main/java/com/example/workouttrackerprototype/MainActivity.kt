package com.example.workouttrackerprototype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.workouttrackerprototype.pages.LandingPage
import com.example.workouttrackerprototype.pages.WorkOutTrackerPrototype
import com.example.workouttrackerprototype.ui.theme.WorkoutTrackerPrototypeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WorkoutTrackerPrototypeTheme {

                var showLandingPage by remember { mutableStateOf(true) }

                Surface(color = MaterialTheme.colors.background) {
                    if (showLandingPage) {
                        LandingPage(onContinueClicked = {
                            showLandingPage = false
                        })
                    } else {
                        WorkOutTrackerPrototype()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWorkOutTrackerPrototype() {
    WorkoutTrackerPrototypeTheme {
        WorkOutTrackerPrototype()
    }}
