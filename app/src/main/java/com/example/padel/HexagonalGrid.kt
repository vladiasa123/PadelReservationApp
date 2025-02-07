package com.example.padel

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

@Composable
fun HexagonalGrid() {
    val hexSize = 30f
    val cols = 15
    val rows = 15
    val spacing = 10f // Space between hexagons

    Canvas(modifier = Modifier.fillMaxSize()) {
        val hexWidth = hexSize * 2 + spacing
        val hexHeight = hexSize * Math.sqrt(3.0).toFloat() + spacing
        val offsetX = hexWidth * 1.4f // Horizontal offset for proper alignment
        val offsetY = hexHeight / 2

        // Calculate the starting Y position to be the center minus 100 pixels
        val startY = 1900f  // Adjust this value to control the starting position

        // Apply a rotation transformation (e.g., 180 degrees)
        rotate(180f) {
            for (row in 0 until rows) {
                for (col in 0 until cols) {
                    var x = col * offsetX
                    var y = startY - (row * hexHeight) // Start from the adjusted center and move upwards

                    if (col % 2 == 1) {
                        // Offset odd columns vertically
                        y += offsetY
                    }

                    val transparency = (1f - (y / size.height)) // Adjust transparency as we go up
                    val color = Color(0f, 0f, 0f, transparency) // Black color with varying transparency

                    drawHexagon(x, y, hexSize, color)
                }
            }
        }
    }
}

fun DrawScope.drawHexagon(x: Float, y: Float, size: Float, color: Color) {
    val path = Path()

    for (i in 0 until 6) {
        val angle = PI / 3 * i
        val xPos = (x + size * cos(angle)).toFloat()
        val yPos = (y + size * sin(angle)).toFloat()
        if (i == 0) {
            path.moveTo(xPos, yPos)
        } else {
            path.lineTo(xPos, yPos)
        }
    }
    path.close()

    drawPath(path, color)
}


