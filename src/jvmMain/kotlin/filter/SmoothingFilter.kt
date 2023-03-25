package ru.nsu.ccfit.plum.filter

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import ru.nsu.ccfit.plum.component.PlumImage

class SmoothingFilter:Filter("Cглаживающий фильтр") {
    override fun draw(image: PlumImage, pressOffset: Offset, releaseOffset: Offset, size: IntSize): PlumImage {
        TODO("Not yet implemented")
    }
}