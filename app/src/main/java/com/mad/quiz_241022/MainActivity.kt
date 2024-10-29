package com.mad.quiz_241022

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorLayout()
        }
    }
}

@Preview(name = "Calculator Layout", showBackground = true)
@Composable
fun CalculatorLayout() {
    var accumulator by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = accumulator.toString(),
            onValueChange = {
                accumulator =
                    if (it.all { char -> char.isDigit() }) it.toInt() else 555
            },
            label = { Text("Accumulator") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CircularCalculatorButton(
                label = "1",
                bgColor = Color.Gray,
                callback = { accumulator += 1 }
            )

            CircularCalculatorButton(
                label = "2",
                bgColor = Color(0xFFFFA500),
                callback = {
                    val prime = getNextPrime(accumulator)
                    accumulator = min(prime, 222)
                }
            )

            CircularCalculatorButton(
                label = "9!",
                bgColor = Color(0xFFFFA500),
                callback = {
                    if (accumulator in 2..10) {
                        val num = factorial(accumulator)
                        Log.d("XU", "$accumulator! is $num")
                        accumulator = num
                    } else {
                        accumulator = 4
                    }
                }
            )

            CircularCalculatorButton(
                label = "AC",
                labelColor = Color.Black,
                bgColor = Color.LightGray,
                callback = {
                    Log.d("XU", "Value before clearing is $accumulator")
                    accumulator = 0
                }
            )
        }
    }
}

@Composable
fun CircularCalculatorButton(
    label: String,
    labelColor: Color = Color.White,
    bgColor: Color = Color.Black,
    callback: () -> Unit
) {
    Button(
        modifier = Modifier
            .clip(CircleShape)
            .size(80.dp),
        onClick = callback,
        colors = ButtonDefaults.buttonColors(
            contentColor = labelColor,
            containerColor = bgColor,
        )
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
    }
}

fun factorial(num: Int): Int =
    if (num <= 1) 1 else num * factorial(num - 1)

fun getNextPrime(num: Int): Int {
    if (!isPrime(num)) {
        return getNextPrime(num + 1)
    }
    return num
}

fun isPrime(num: Int): Boolean {
    if (num <= 2) {
        return true
    }
    var i = 3
    while (i < num) {
        if (num % i == 0) {
            return false
        }
        i++
    }
    return true
}