package ru.nsu.ccfit.plum.tool.filter

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.draw.getImageFilter
import java.awt.image.BufferedImage

object GrayScaleFilter : Filter("Черно-белый фильтр") {
    private var wR = 0.21
    private var wG = 0.72
    private var wB = 0.07

    override fun permit(image: PlumImage): PlumImage {
        val newImage = image.copy()
        newImage.getImageFilter { x, y -> getGrayColor(image, x, y) }
        return newImage
    }

    private fun getGrayColor(image: BufferedImage, x: Int, y: Int): Int {
        val pixel = image.getRGB(x, y)
        val alpha = pixel shr 24 and 0xff
        val red = pixel shr 16 and 0xff
        val green = pixel shr 8 and 0xff
        val blue = pixel and 0xff
        val grayValue: Int =
            (wR * red + wG * green + wB * blue).toInt()
        return alpha shl 24 or (grayValue shl 16) or (grayValue shl 8) or grayValue
    }

}