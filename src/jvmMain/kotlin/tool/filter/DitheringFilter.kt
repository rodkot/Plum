package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.draw.getImageFilter
import java.awt.Color
import kotlin.math.roundToInt

object DitheringFilter : Filter("Дизеринг") {

    var quantizationRed = 2
    var quantizationBlue = 2
    var quantizationGreen = 2

    fun ditherImage(image: PlumImage, levels: Int) {
        val width = image.width
        val height = image.height
        val redLevels = levels.coerceIn(2, 128)
        val greenLevels = levels.coerceIn(2, 128)
        val blueLevels = levels.coerceIn(2, 128)

        for (y in 0 until height) {
            for (x in 0 until width) {
                var oldColor = Color(image.getRGB(x, y))
                var newColor = Color(
                    quantizeColor(oldColor.red, redLevels),
                    quantizeColor(oldColor.green, greenLevels),
                    quantizeColor(oldColor.blue, blueLevels)
                )
                image.setRGB(x, y, newColor.rgb)

                // Compute the quantization error
                val quantErrorRed = oldColor.red - newColor.red
                val quantErrorGreen = oldColor.green - newColor.green
                val quantErrorBlue = oldColor.blue - newColor.blue

                // Propagate the error to neighboring pixels
                if (x < width - 1) {
                    propagateError(
                        image,
                        x + 1,
                        y,
                        quantErrorRed,
                        quantErrorGreen,
                        quantErrorBlue,
                        7,
                        redLevels,
                        greenLevels,
                        blueLevels
                    )
                }
                if (x > 0 && y < height - 1) {
                    propagateError(
                        image,
                        x - 1,
                        y + 1,
                        quantErrorRed,
                        quantErrorGreen,
                        quantErrorBlue,
                        3,
                        redLevels,
                        greenLevels,
                        blueLevels
                    )
                }
                if (y < height - 1) {
                    propagateError(
                        image,
                        x,
                        y + 1,
                        quantErrorRed,
                        quantErrorGreen,
                        quantErrorBlue,
                        5,
                        redLevels,
                        greenLevels,
                        blueLevels
                    )
                }
                if (x < width - 1 && y < height - 1) {
                    propagateError(
                        image,
                        x + 1,
                        y + 1,
                        quantErrorRed,
                        quantErrorGreen,
                        quantErrorBlue,
                        1,
                        redLevels,
                        greenLevels,
                        blueLevels
                    )
                }
            }
        }
    }

    fun propagateError(
        image: PlumImage,
        x: Int,
        y: Int,
        quantErrorRed: Int,
        quantErrorGreen: Int,
        quantErrorBlue: Int,
        weight: Int,
        redLevels: Int,
        greenLevels: Int,
        blueLevels: Int
    ) {
        val oldColor = Color(image.getRGB(x, y))
        val newColor = Color(
            quantizeColor(oldColor.red + quantErrorRed * weight / 16, redLevels),
            quantizeColor(oldColor.green + quantErrorGreen * weight / 16, greenLevels),
            quantizeColor(oldColor.blue + quantErrorBlue * weight / 16, blueLevels)
        )
        image.setRGB(x, y, newColor.rgb)
    }

    fun quantizeColor(color: Int, levels: Int): Int {
        val stepSize = 255.0 / (levels - 1)
        return (stepSize * (color / stepSize).roundToInt()).toInt().coerceIn(0, 255)
    }

    fun ditheringFloydSteinberg(image: PlumImage) {
        for (y in 0 until image.height) {
            for (x in 0 until image.width) {
                val (red,green,blue) = image.getPixel(x,y)

                val newRed = (4*red/255).rou
            }
        }
    }

    override fun permit(image: PlumImage): PlumImage {
        val newImage = GrayScaleFilter.permit(image)


        // ditherImage(newImage, quantizationRed)
//        val quantError = Array(newImage.height) { FloatArray(newImage.width) } // quantization error buffer
//        val colorStepRed = 256 / quantizationRed // color step size
//        val colorStepGreen = 256 / quantizationGreen // color step size
//        val colorStepBlue = 256 / quantizationBlue // color step size
//
//        for (y in 0 until newImage.height) {
//            for (x in 0 until newImage.width) {
//                val oldColor = Color(newImage.getRGB(x, y))
//                val newColor = Color(
//                    (oldColor.red / colorStepRed) * colorStepRed, // round red component
//                    (oldColor.green / colorStepGreen) * colorStepGreen, // round green component
//                    (oldColor.blue / colorStepBlue) * colorStepBlue  // round blue component
//                )
//                newImage.setRGB(x, y, newColor.rgb)
//
//                // compute quantization error
//                val quantErrorR = oldColor.red - newColor.red
//                val quantErrorG = oldColor.green - newColor.green
//                val quantErrorB = oldColor.blue - newColor.blue
//
//                //Обработка граничных условий
//                if (x == 0 || x == newImage.width - 1 || y == newImage.height - 1) {
//                    if (y < newImage.height - 1) {
//                        if (x == newImage.width - 1) {
//                            quantError[y + 1][x] += quantErrorR * 7.0f / 16.0f
//                            quantError[y + 1][x] += quantErrorG * 7.0f / 16.0f
//                            quantError[y + 1][x] += quantErrorB * 7.0f / 16.0f
//
//                            quantError[y][x - 1] += quantErrorR * 9.0f / 16.0f
//                            quantError[y][x - 1] += quantErrorG * 9.0f / 16.0f
//                            quantError[y][x - 1] += quantErrorB * 9.0f / 16.0f
//                        }
//
//                        if (x == 0) {
//                            quantError[y][x + 1] += quantErrorR * 3.0f / 16.0f
//                            quantError[y][x + 1] += quantErrorG * 3.0f / 16.0f
//                            quantError[y][x + 1] += quantErrorB * 3.0f / 16.0f
//
//                            quantError[y + 1][x] += quantErrorR * 5.0f / 16.0f
//                            quantError[y + 1][x] += quantErrorG * 5.0f / 16.0f
//                            quantError[y + 1][x] += quantErrorB * 5.0f / 16.0f
//
//                            quantError[y + 1][x + 1] += quantErrorR * 8.0f / 16.0f
//                            quantError[y + 1][x + 1] += quantErrorG * 8.0f / 16.0f
//                            quantError[y + 1][x + 1] += quantErrorB * 8.0f / 16.0f
//                        }
//                    } else {
//                        if (x < newImage.width - 1) {
//                            quantError[y][x + 1] += quantErrorR.toFloat()
//                            quantError[y][x + 1] += quantErrorG.toFloat()
//                            quantError[y][x + 1] += quantErrorB.toFloat()
//                        }
//                    }
//                } else {
//                    quantError[y][x + 1] += quantErrorR * 7.0f / 16.0f
//                    quantError[y][x + 1] += quantErrorG * 7.0f / 16.0f
//                    quantError[y][x + 1] += quantErrorB * 7.0f / 16.0f
//
//                    quantError[y + 1][x - 1] += quantErrorR * 3.0f / 16.0f
//                    quantError[y + 1][x - 1] += quantErrorG * 3.0f / 16.0f
//                    quantError[y + 1][x - 1] += quantErrorB * 3.0f / 16.0f
//
//                    quantError[y + 1][x] += quantErrorR * 5.0f / 16.0f
//                    quantError[y + 1][x] += quantErrorG * 5.0f / 16.0f
//                    quantError[y + 1][x] += quantErrorB * 5.0f / 16.0f
//
//                    quantError[y + 1][x + 1] += quantErrorR * 8.0f / 16.0f
//                    quantError[y + 1][x + 1] += quantErrorG * 8.0f / 16.0f
//                    quantError[y + 1][x + 1] += quantErrorB * 8.0f / 16.0f
//
//                }
//            }
//        }
        return newImage
    }
}