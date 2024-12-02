package com.example.matrixproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
          Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              verticalArrangement = Arrangement.Top,
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              GreetingText("Matrix Generator")
              Spacer(modifier = Modifier.height(16.dp))
              MatrixInput()
            }
          }
        }
      }
    }
  }

  @Composable
  fun MatrixOutput(matrixSize: Int, matrixString: String) {
    val lines = matrixString.split("\n").filter { it.isNotEmpty() }
    val fontSize = 12.sp

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
      for ((row, line) in lines.withIndex()) {
        Row(
          modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          val numbers = line.trim().split(" ")
          for ((col, number) in numbers.withIndex()) {
            val isDiagonal = row == col
            Text(
              text = number,
              modifier = Modifier
                .padding(4.dp)
                .weight(1f),
              fontSize = fontSize,
              fontFamily = FontFamily.Monospace,
              textAlign = TextAlign.Center,

            )
          }
        }
      }
    }
  }

  @Composable
  fun GreetingText(message: String, modifier: Modifier = Modifier) {
    Text(text = message, modifier = modifier)
  }

  @Composable
  fun MatrixInput() {
    var text by remember { mutableStateOf("") }
    var size by remember { mutableStateOf(0) }
    var results by remember { mutableStateOf("") }

    TextField(value = text, onValueChange = {
      text = it
      size = it.toIntOrNull() ?: 0
    }, label = {
      Text("Numbers here", color = Color.White)
    })



    Spacer(modifier = Modifier.height(16.dp))

    Button(
      onClick = {
        results = printMatrix(size)
      },
      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC0CB))
    ) {
      Text("Produce")
    }

    Spacer(modifier = Modifier.height(16.dp))

    if (results.isNotEmpty()) {
      MatrixOutput(size, results)
    }
  }

  private fun printMatrix(size: Int): String {
    val matrix = Array(size) { IntArray(size) }
    val output = StringBuilder()

    output.append("Printing matrix with values: \n")
    output.append(populateMatrix(matrix))

    output.append("\nPrinting populated matrix: \n")
    output.append(printMatrix(matrix))

    flipMatrix(matrix)
    output.append("\nPrinting flipped matrix: \n")
    output.append(printMatrix(matrix))

    return output.toString()
  }

  private fun printMatrix(matrix: Array<IntArray>): String {
    val output = StringBuilder()
    for (row in matrix) {
      output.append(row.joinToString(" ") { it.toString() })
      output.append("\n")
    }
    return output.toString()
  }

  private fun populateMatrix(matrix: Array<IntArray>): String {
    var counter = 1
    for (i in matrix.indices) {
      for (j in matrix[i].indices) {
        matrix[i][j] = counter++
      }
    }
    return printMatrix(matrix)
  }

  private fun swapMatrix(matrix: Array<IntArray>, x1: Int, y1: Int, x2: Int, y2: Int) {
    val temp = matrix[x1][y1]
    matrix[x1][y1] = matrix[x2][y2]
    matrix[x2][y2] = temp
  }

  private fun flipMatrix(matrix: Array<IntArray>) {
    val size = matrix.size
    for (i in 0 until size) {
      for (j in 0 until size - i - 1) {
        swapMatrix(matrix, i, j, size - j - 1, size - i - 1)
      }
    }
  }
}
