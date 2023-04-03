package ru.nsu.ccfit.plum.tool

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import ru.nsu.ccfit.plum.component.PlumImage

abstract class Tool(val name:String){
    abstract fun draw(image: PlumImage, pressOffset: Offset = Offset.Zero, releaseOffset: Offset = Offset.Zero, size: IntSize = IntSize.Zero): PlumImage
}