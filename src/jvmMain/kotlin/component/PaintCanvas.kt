package ru.nsu.ccfit.plum.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp


class PaintCanvas() {
    private var isPaint: Boolean = true

    private val offsetClick = mutableStateOf(Offset(0f, 0f) to Offset(0f, 0f))

    private var size: IntSize = IntSize.Zero
    private var offsetPress = (Offset(0F, 0F))
    private var offsetRelease = (Offset(0F, 0F))


    fun start() {
        isPaint = true
    }

    fun stop() {
        isPaint = false
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun render(image: PlumImage, onClick: (PlumImage, Offset, Offset) -> Unit) {
        var positionRelease: Offset
        var positionPress: Offset

        Box(Modifier.border(3.dp, Color.Black)) {
            Box(Modifier.fillMaxSize()) {
                Image(image.toComposeImageBitmap(), contentDescription = "Изображение",
                    modifier = Modifier.fillMaxSize()
                        .onPointerEvent(PointerEventType.Release) {
                            positionRelease = it.changes.first().position
                            offsetRelease = positionRelease
                            if (0 <= positionRelease.x && positionRelease.x < size.width) {
                                if (0 <= positionRelease.y && positionRelease.y < size.height) {
                                    if (isPaint) {
                                        offsetClick.value = offsetPress to offsetRelease
                                        onClick.invoke(image, offsetPress, offsetRelease)
                                    }
                                }
                            }
                        }.onPointerEvent(PointerEventType.Press) {
                            positionPress = it.changes.first().position
                            offsetPress = positionPress
                        })
            }

        }

    }
}