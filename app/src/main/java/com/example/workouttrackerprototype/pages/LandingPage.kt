package com.example.workouttrackerprototype.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LandingPage(onContinueClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Dewi's Prototype Workout Tracker!", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(24.dp))
        Text("This thing is only going to get better! I promise! 👀", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onContinueClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Let's go, Big man! What you got so far?")
        }
    }
}