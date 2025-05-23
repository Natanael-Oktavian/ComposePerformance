package com.example.composeperformance

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeperformance.ui.theme.ComposePerformanceTheme
import kotlinx.coroutines.delay
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalInspectionMode
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposePerformanceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StateEffectsDemo(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun StateEffectsDemo(modifier: Modifier) {
    CompositionLocalProvider(
    LocalInspectionMode provides true
) {
        val context = LocalContext.current
        var isRunning by remember { mutableStateOf(false) }
        var counter by remember { mutableStateOf(0) }
        var toastMessage by remember { mutableStateOf("Timer started") }

        val isMultiplierOfFive = remember {
            derivedStateOf { counter % 5 == 0 }
        }

        val updatedToast = rememberUpdatedState(toastMessage)

        LaunchedEffect(isRunning) {
            if (isRunning) {
                while (true) {
                    delay(1000)
                    counter++
                    Toast.makeText(context, updatedToast.value, Toast.LENGTH_SHORT).show()
                }
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            LaunchedEffect(counter) {
                Log.d("RecomposeLog", "Counter is : ${counter}")
            }
            LaunchedEffect(isMultiplierOfFive.value) {
                Log.d("RecomposeLog", "Counter is multiple of 5: ${isMultiplierOfFive.value}")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Count: $counter", fontSize = 24.sp)
            Text("Is count multiplier of 5: ${isMultiplierOfFive.value}")
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { isRunning = !isRunning }) {
                Text(if (isRunning) "Stop" else "Start")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = toastMessage,
                onValueChange = { toastMessage = it },
                label = { Text("Toast Message") }
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposePerformanceTheme {
        Greeting("Android")
    }
}