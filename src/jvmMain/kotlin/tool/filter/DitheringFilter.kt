package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.Param
import ru.nsu.ccfit.plum.component.PlumImage
import java.awt.Color
import java.awt.image.BufferedImage


object DitheringFilter : Filter("Дизеринг") {

    enum class Dithering : Param {
        FLOYD_STEINBERG,
        ORDERED;

        override fun getName(): String {
            return toString()
        }
    }

    var dithering = Dithering.ORDERED
    var quantizationRed = 2
    var quantizationBlue = 2
    var quantizationGreen = 2


    private fun orderedDithering(
        inputImage: PlumImage,
        ditheringMatrix: Array<IntArray>,
        quantizationLevels: Int
    ): PlumImage {
        val width = inputImage.width
        val height = inputImage.height
        val outputImage = PlumImage(width, height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                // Get the color of the input pixel
                val inputPixel = inputImage.getRGB(x, y)
                val inputRed = inputPixel shr 16 and 0xff
                val inputGreen = inputPixel shr 8 and 0xff
                val inputBlue = inputPixel and 0xff

                // Calculate the dithering thresholds for each color channel
                val thresholdRed: Int =
                    ditheringMatrix[x % ditheringMatrix.size][y % ditheringMatrix.size] * (256 / quantizationLevels)
                val thresholdGreen: Int =
                    ditheringMatrix[(x + 1) % ditheringMatrix.size][(y + 1) % ditheringMatrix.size] * (256 / quantizationLevels)
                val thresholdBlue: Int =
                    ditheringMatrix[(x + 2) % ditheringMatrix.size][(y + 2) % ditheringMatrix.size] * (256 / quantizationLevels)

                // Set the output pixel value based on the dithering thresholds for each color channel
                val outputRed = if (inputRed < thresholdRed) 0 else 255
                val outputGreen = if (inputGreen < thresholdGreen) 0 else 255
                val outputBlue = if (inputBlue < thresholdBlue) 0 else 255
                outputImage.setRGB(x, y, Color(outputRed, outputGreen, outputBlue).rgb)
            }
        }
        return outputImage
    }

    private fun generateDitheringMatrix(size: Int): Array<IntArray> {
        val matrix = Array(size) { IntArray(size) }
        val threshold = 255 / (size * size - 1)

        for (y in 0 until size) {
            for (x in 0 until size) {
                val value = (y * size + x) * threshold
                matrix[x][y] = value
            }
        }

        return matrix
    }


    private fun ditheringFloydSteinberg(imageToProcess: PlumImage) {
        val width = imageToProcess.width
        val height = imageToProcess.height
        var oldPixel: Color
        for (i in 0 until height) {
            for (j in 0 until width) {
                oldPixel = Color(imageToProcess.getRGB(j, i))
                processPixel(imageToProcess, oldPixel, j, i)
                spreadErrors(imageToProcess, oldPixel, j, i)
            }
        }
    }

    private fun spreadErrors(image: PlumImage, oldPixel: Color, i: Int, j: Int) {
        val newPixel = Color(image.getRGB(i, j))
        val redError = oldPixel.red - newPixel.red
        val greenError = oldPixel.green - newPixel.green
        val blueError = oldPixel.blue - newPixel.blue
        spreadErrorToPixel(image, i + 1, j, redError, greenError, blueError, 7 / 16.0)
        spreadErrorToPixel(image, i - 1, j + 1, redError, greenError, blueError, 3 / 16.0)
        spreadErrorToPixel(image, i, j + 1, redError, greenError, blueError, 5 / 16.0)
        spreadErrorToPixel(image, i + 1, j + 1, redError, greenError, blueError, 1 / 16.0)
    }

    private fun processPixel(image: PlumImage, oldPixel: Color, i: Int, j: Int) {
        val colorStepR = 255 / quantizationRed
        val colorStepG = 255 / quantizationGreen
        val colorStepB = 255 / quantizationBlue
        val newColor: Color = Color(
            (oldPixel.red / colorStepR) * colorStepR, // round red component
            (oldPixel.green / colorStepG) * colorStepG, // round green component
            (oldPixel.blue / colorStepB) * colorStepB // round blue component
        )
        image.setRGB(i, j, newColor.rgb)
    }

    private fun spreadErrorToPixel(
        image: PlumImage,
        i: Int,
        j: Int,
        redError: Int,
        greenError: Int,
        blueError: Int,
        multiplyValue: Double
    ) {
        if (outOfBounds(image, i, j)) {
            //TODO:Обработать границы
            return
        }
        val pixelToProcess = Color(image.getRGB(i, j))
        val newRed = (pixelToProcess.red + redError * multiplyValue).toInt()
        val newGreen = (pixelToProcess.green + greenError * multiplyValue).toInt()
        val newBlue = (pixelToProcess.blue + blueError * multiplyValue).toInt()
        val processedPixel = Color(normalizeColor(newRed), normalizeColor(newGreen), normalizeColor(newBlue))
        image.setRGB(i, j, processedPixel.rgb)
    }

    private fun outOfBounds(image: BufferedImage, x: Int, y: Int): Boolean {
        return x < 0 || y < 0 || x > image.width - 1 || y > image.height - 1
    }

    private fun normalizeColor(value: Int): Int {
        if (value < 0) {
            return 0
        }
        return if (value >= 255) {
            255
        } else value
    }

    override fun permit(image: PlumImage): PlumImage {
        val newImage = image.copy()
        when (dithering) {
            Dithering.FLOYD_STEINBERG -> ditheringFloydSteinberg(newImage)
            Dithering.ORDERED -> return orderedDithering(newImage, generateDitheringMatrix(16), quantizationRed)
        }

        return newImage
    }
}