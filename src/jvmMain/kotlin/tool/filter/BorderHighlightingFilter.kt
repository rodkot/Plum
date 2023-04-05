package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.Param
import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.tool.filter.GrayScaleFilter.getGrayValue
import java.awt.Color
import kotlin.math.sqrt


object BorderHighlightingFilter : Filter("Фильр выделения границ") {
    var algorithm: Algorithm = Algorithm.ROBERT
    var binarization = 128

    enum class Algorithm : Param {
        ROBERT,
        SOBEL;

        override fun getName(): String {
            return toString()
        }
    }

    private fun applyRobertsFilter(image: PlumImage): PlumImage {
        val w = image.width
        val h = image.height
        val result = PlumImage(w, h)

        for (x in 1 until w - 1) {
            for (y in 1 until h - 1) {
                // Подчет градиента
                val gx = getGrayValue(image.getRGB(x, y)) - getGrayValue(image.getRGB(x + 1, y + 1))
                val gy = getGrayValue(image.getRGB(x, y + 1)) - getGrayValue(image.getRGB(x + 1, y))

                val magnitude = sqrt((gx * gx + gy * gy).toDouble())
                val color = if (magnitude > binarization) Color.WHITE.rgb else Color.BLACK.rgb

                result.setRGB(x, y, color)
            }
        }

        return result
    }

    private fun applySobelFilter(image: PlumImage): PlumImage {
        val w = image.width
        val h = image.height
        val result = PlumImage(w, h)

        val sobelX = arrayOf(intArrayOf(1, 0, -1), intArrayOf(2, 0, -2), intArrayOf(1, 0, -1))
        val sobelY = arrayOf(intArrayOf(1, 2, 1), intArrayOf(0, 0, 0), intArrayOf(-1, -2, -1))

        for (x in 1 until w - 1) {
            for (y in 1 until h - 1) {
                // Подчет градиента
                var gx = 0
                var gy = 0
                for (i in -1..1) {
                    for (j in -1..1) {
                        gx += getGrayValue(image.getRGB(x + i, y + j)) * sobelX[i + 1][j + 1]
                        gy += getGrayValue(image.getRGB(x + i, y + j)) * sobelY[i + 1][j + 1]
                    }
                }

                val magnitude = sqrt((gx * gx + gy * gy).toDouble())
                val color = if (magnitude > binarization) Color.WHITE.rgb else Color.BLACK.rgb

                result.setRGB(x, y, color)
            }
        }

        return result
    }

    override fun permit(image: PlumImage): PlumImage = when (algorithm) {
        Algorithm.ROBERT -> {
            applyRobertsFilter(image)
        }

        Algorithm.SOBEL -> {
            applySobelFilter(image)
        }
    }
}