package ru.nsu.ccfit.plum.icon.impl

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import ru.nsu.ccfit.plum.icon.Icon


/**
 * Класс отоборажающий иконку из векторной картинки
 */
class VectorIcon(private val image: ImageVector) : Icon() {

    @Composable
    override fun render() {
        Icon(image, contentDescription = image.name)
    }
}