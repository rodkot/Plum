package ru.nsu.ccfit.plum.tool.filter

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import ru.nsu.ccfit.plum.component.PlumImage
import kotlin.math.max
import kotlin.math.min

object EmbossingFilter : Filter("Тиснение") {
    private val embossKernel = arrayOf(intArrayOf(0, 1, 0), intArrayOf(-1, 0, 1), intArrayOf(0, -1, 0))

    override fun draw(image: PlumImage, pressOffset: Offset, releaseOffset: Offset, size: IntSize): PlumImage {
        val grayScaleImage = GrayScaleFilter.draw(image)
        val width = grayScaleImage.width
        val height = grayScaleImage.height
        val result = PlumImage(width, height)

        for (y in 1 until height - 1) {
            for (x in 1 until width - 1) {
                var sumR = 0
                var sumG = 0
                var sumB = 0

                // Умножение каждого пикселя в ядре на соответствующий пиксель в изображении
                for (kernelY in 0..2) {
                    for (kernelX in 0..2) {
                        val imageX = x + kernelX - 1
                        val imageY = y + kernelY - 1
                        val (r,g,b) = grayScaleImage.getPixel(imageX, imageY)
                        val kernelValue = embossKernel[kernelY][kernelX]

                        // Извлечение компоненты RGB из пикселя изображения
                        sumR += r * kernelValue
                        sumG += g * kernelValue
                        sumB += b * kernelValue
                    }
                }


                // Нормализация
                val r = min(max(sumR + 128, 0), 255)
                val g = min(max(sumG + 128, 0), 255)
                val b = min(max(sumB + 128, 0), 255)

                // Combine the RGB components into a single pixel value
                val pixel = r shl 16 or (g shl 8) or b
                result.setRGB(x, y, pixel)
            }
        }

        return result
    }
}