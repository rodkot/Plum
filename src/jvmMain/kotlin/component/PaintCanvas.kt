package ru.nsu.ccfit.plum.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp


class PaintCanvas() {
    private var isPaint: Boolean = true

    fun start() {
        isPaint = true
    }

    fun stop() {
        isPaint = false
    }

    @Composable
    fun render(image: PlumImage) {

        Box(Modifier.border(3.dp, Color.Black)) {
            Box(Modifier.fillMaxSize()) {
                Image(
                    image.toComposeImageBitmap(), contentDescription = "Изображение",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}