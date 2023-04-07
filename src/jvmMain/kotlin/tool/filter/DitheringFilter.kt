package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.draw.getImageFilter
import ru.nsu.ccfit.plum.draw.getIntRGB
import java.awt.Color
import java.lang.Error
import kotlin.math.roundToInt

object DitheringFilter : Filter("Дизеринг") {

    var quantizationRed = 2
    var quantizationBlue = 2
    var quantizationGreen = 2


    fun ditheringFloydSteinberg(image: PlumImage, numColors: Int) {
        val quantError = Array(image.height) { FloatArray(image.width) } // quantization error buffer
        val colorStep = 256 / numColors // color step size

        for (y in 0 until image.height) {
            for (x in 0 until image.width) {
                val oldColor = Color(image.getRGB(x, y))
                val newColor = Color(
                    (oldColor.red / colorStep) * colorStep, // round red component
                    (oldColor.green / colorStep) * colorStep, // round green component
                    (oldColor.blue / colorStep) * colorStep // round blue component
                )
                image.setRGB(x, y, newColor.rgb)

                // compute quantization error
                val quantErrorR = oldColor.red - newColor.red
                val quantErrorG = oldColor.green - newColor.green
                val quantErrorB = oldColor.blue - newColor.blue

                // propagate quantization error to neighboring pixels
                if (x < image.width - 1) {
                    quantError[y][x + 1] += quantErrorR * 7.0f / 16.0f
                    quantError[y][x + 1] += quantErrorG * 7.0f / 16.0f
                    quantError[y][x + 1] += quantErrorB * 7.0f / 16.0f
                }
                if (y < image.height - 1) {
                    if (x > 0) {
                        quantError[y + 1][x - 1] += quantErrorR * 3.0f / 16.0f
                        quantError[y + 1][x - 1] += quantErrorG * 3.0f / 16.0f
                        quantError[y + 1][x - 1] += quantErrorB * 3.0f / 16.0f
                    }
                    quantError[y + 1][x] += quantErrorR * 5.0f / 16.0f
                    quantError[y + 1][x] += quantErrorG * 5.0f / 16.0f
                    quantError[y + 1][x] += quantErrorB * 5.0f / 16.0f
                    if (x < image.width - 1) {
                        quantError[y + 1][x + 1] += quantErrorR * 1.0f / 16.0f
                        quantError[y + 1][x + 1] += quantErrorG * 1.0f / 16.0f
                        quantError[y + 1][x + 1] += quantErrorB * 1.0f / 16.0f
                    }
                }
            }
        }
    }
    fun setError(image: PlumImage, x: Int, y: Int, redError: Int, greenError: Int, blueError: Int, weight: Int) {
        val (red, green, blue) = image.getPixel(x, y)
        image.setRGB(
            x,
            y,
            Triple(
                red + redError * weight / 16,
                green + greenError * weight / 16,
                blue + blueError * weight / 16
            ).getIntRGB()
        )
    }

    override fun permit(image: PlumImage): PlumImage {
        val newImage = GrayScaleFilter.permit(image)
        ditheringFloydSteinberg(newImage, quantizationRed)
        return newImage
    }
}