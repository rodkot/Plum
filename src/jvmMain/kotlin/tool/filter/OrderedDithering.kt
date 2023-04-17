package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.draw.getImageFilter
import ru.nsu.ccfit.plum.tool.filter.DitheringFilter as DF

object OrderedDithering {
    private lateinit var thresholdMap: Array<DoubleArray>

    private var matrixSize = 4
    private val firstKernel = arrayOf(
        doubleArrayOf(0.0, 2.0),
        doubleArrayOf(3.0, 1.0)
    )

    private fun createThresholdMap(size: Int): Array<DoubleArray> {
        if (size == 2) return firstKernel

        val oldKernel: Array<DoubleArray> = createThresholdMap(size / 2)
        val length = oldKernel[0].size
        val newMatrix = Array(2 * length) { DoubleArray(2 * length) }

        for (x in 0 until length) {
            for (y in 0 until length) {
                newMatrix[x][y] = 4 * oldKernel[x % length][y % length]
            }
        }

        for (x in length until 2 * length) {
            for (y in 0 until length) {
                newMatrix[x][y] = 4 * oldKernel[x % length][y % length] + 2
            }
        }

        for (x in 0 until length) {
            for (y in length until 2 * length) {
                newMatrix[x][y] = 4 * oldKernel[x % length][y % length] + 3
            }
        }

        for (x in length until 2 * length) {
            for (y in length until 2 * length) {
                newMatrix[x][y] = 4 * oldKernel[x % length][y % length] + 1
            }
        }

        return newMatrix
    }

    private fun normalize() {
        for (x in 0 until matrixSize) {
            for (y in 0 until matrixSize) {
                thresholdMap[x][y] = (thresholdMap[x][y] + 1) / (matrixSize * matrixSize)
            }
        }
    }

    private fun findMatrixSize(): Int {
        val maxSize = doubleArrayOf(
            256.0 / DF.redQuantizationNum,
            256.0 / DF.greenQuantizationNum,
            256.0 / DF.blueQuantizationNum
        ).max()

        var finalSize = 0

        arrayOf(2, 4, 8, 16).forEach {
            if (maxSize <= it * it) {
                finalSize = it
                return finalSize
            }
        }

        return finalSize
    }

    fun permit(image: PlumImage): PlumImage {
        val newImage = image.copy()

        matrixSize = findMatrixSize()
        DF.createPalette()
        thresholdMap = createThresholdMap(matrixSize)
        normalize()

        val stepR = 256.0 / DF.rPalette.size
        val stepG = 256.0 / DF.gPalette.size
        val stepB = 256.0 / DF.bPalette.size

        newImage.getImageFilter { x, y ->
            val color = image.getRGB(x, y)
            val thresholdElem = thresholdMap[y % matrixSize][x % matrixSize]

            getNewColor(
                DF.findColorInPalette(DF.rPalette, (color.red() + stepR * thresholdElem).toInt()),
                DF.findColorInPalette(DF.gPalette, (color.green() + stepG * thresholdElem).toInt()),
                DF.findColorInPalette(DF.bPalette, (color.blue() + stepB * thresholdElem).toInt())
            )
        }

        return newImage
    }
}