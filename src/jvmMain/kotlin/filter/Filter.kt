package ru.nsu.ccfit.plum.filter

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import ru.nsu.ccfit.plum.component.PlumImage


abstract class Filter(name: String) : Tool(name) {

    /**
     *
     */
    abstract fun draw(image: PlumImage, pressOffset: Offset, releaseOffset: Offset, size: IntSize)

}