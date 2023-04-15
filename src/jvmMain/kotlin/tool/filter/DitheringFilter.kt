package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.Param
import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.draw.getIntRGB
import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.roundToInt


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


    private fun orderedDithering(image: PlumImage): PlumImage {
        val width = image.width
        val height = image.height
        val outputImage = PlumImage(width, height)
        val matrixRed = generateThresholdMap(if (quantizationRed % 2 == 0) quantizationRed else quantizationRed + 1)
        val matrixBlue = generateThresholdMap(if (quantizationBlue % 2 == 0) quantizationBlue else quantizationBlue + 1)
        val matrixGreen = generateThresholdMap(if (quantizationGreen % 2 == 0) quantizationGreen else quantizationGreen + 1)
        for (x in 0 until width) {
            for (y in 0 until height) {
                val (red, green, blue) = image.getPixel(x, y)

                val newRed = getOrderedColor(red, matrixRed, x, y, quantizationRed)
                val newGreen = getOrderedColor(green, matrixGreen, x, y, quantizationGreen)
                val newBlue = getOrderedColor(blue, matrixBlue, x, y, quantizationBlue)

                outputImage.setRGB(x, y, getIntRGB(newRed, newGreen, newBlue))
            }
        }
        return outputImage
    }

    private fun getOrderedColor(value: Int, matrix: Array<IntArray>, x: Int, y: Int, spread: Int): Int {
        val rv = 256 / (spread - 1)
        val matrixDim = matrix.size
        val threshold = (rv * (matrix[x % matrixDim][y % matrixDim].toDouble() / (matrixDim * matrixDim) - 0.5)).toInt()
        return colorStep(value + threshold, rv)
    }

    private fun colorStep(value: Int, stepSize: Int): Int {
        return normalizeColor(((value.toDouble() / stepSize).roundToInt() * stepSize))
    }

    private fun generateThresholdMap(size: Int): Array<IntArray> {
        val map = Array(size) { IntArray(size) }
        if (size == 1) {
            map[0][0] = 0
        } else {
            val subMap = generateThresholdMap(size / 2)
            for (i in 0 until size) {
                for (j in 0 until size) {
                    val x = i % (size / 2)
                    val y = j % (size / 2)
                    map[i][j] = 4 * subMap[x][y] + (i / (size / 2)) * 2 + (j / (size / 2))
                }
            }
        }
        return map
    }

    private fun ditheringFloydSteinberg(image: PlumImage): PlumImage {
        val imageToProcess = image.copy()
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
        return imageToProcess
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
        val newColor = Color(
            (oldPixel.red / colorStepR) * colorStepR,
            (oldPixel.green / colorStepG) * colorStepG,
            (oldPixel.blue / colorStepB) * colorStepB
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
        return when (dithering) {
            Dithering.FLOYD_STEINBERG -> ditheringFloydSteinberg(image)
            Dithering.ORDERED -> orderedDithering(image)
        }
    }
}