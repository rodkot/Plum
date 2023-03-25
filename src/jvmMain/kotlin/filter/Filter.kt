package ru.nsu.ccfit.plum.filter

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import ru.nsu.ccfit.plum.component.PlumImage


abstract class Filter(private val name: String) {
    abstract fun draw(image: PlumImage, pressOffset: Offset, releaseOffset: Offset, size: IntSize): PlumImage

    fun getNameTool(): String {
        return name
    }
}