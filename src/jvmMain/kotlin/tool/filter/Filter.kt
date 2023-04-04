package ru.nsu.ccfit.plum.tool.filter

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.tool.Tool

abstract class Filter(name: String) : Tool(name) {
    /**
     * Функция применяет фильр к изображению
     * @param image исходное изображение
     * @return возвращает отфильрованное изображение
     */
    abstract fun permit(image: PlumImage): PlumImage

    final override fun draw(image: PlumImage, pressOffset: Offset, releaseOffset: Offset, size: IntSize): PlumImage {
        return permit(image)
    }
}