package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.draw.getImageFilter

object GrayScaleFilter : Filter("Черно-белый фильтр") {
    private var wR = 0.21
    private var wG = 0.72
    private var wB = 0.07

    override fun permit(image: PlumImage): PlumImage {
        val newImage = image.copy()
        newImage.getImageFilter { x, y -> getGrayColor(image, x, y) }
        return newImage
    }

    fun getGrayValue(rgb: Int): Int {
        val red = (rgb shr 16) and 0xff
        val green = (rgb shr 8) and 0xff
        val blue = rgb and 0xff
        return  (wR * red + wG * green + wB * blue).toInt()
    }

    private fun getGrayColor(image: PlumImage, x: Int, y: Int): Int {
        val grayValue: Int =
           getGrayValue(image.getRGB(x, y))
        return (grayValue shl 16) or (grayValue shl 8) or grayValue
    }

}