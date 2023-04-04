package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.draw.getIntRGB
import ru.nsu.ccfit.plum.draw.getValueFilter
import kotlin.math.max
import kotlin.math.min

object EmbossingFilter : Filter("Тиснение") {
    private val embossKernel = arrayOf(intArrayOf(0, 1, 0), intArrayOf(-1, 0, 1), intArrayOf(0, -1, 0))

    override fun permit(image: PlumImage): PlumImage {
        val grayScaleImage = GrayScaleFilter.draw(image)
        val width = grayScaleImage.width
        val height = grayScaleImage.height
        val result = PlumImage(width, height)

        for (y in 1 until height - 1) {
            for (x in 1 until width - 1) {
                val (sumR,sumG,sumB) = grayScaleImage.getValueFilter(embossKernel,x-1, y-1)

                // Нормализация
                val r = min(max(sumR + 128, 0), 255)
                val g = min(max(sumG + 128, 0), 255)
                val b = min(max(sumB + 128, 0), 255)

                result.setRGB(x, y, Triple(r,g,b).getIntRGB())
            }
        }

        return result
    }
}