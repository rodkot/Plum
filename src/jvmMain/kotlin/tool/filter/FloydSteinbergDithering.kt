package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object FloydSteinbergDithering : Filter("Дизеринг, алгоритм Флойда Стейнберга") {
    private const val redQuantizationNum = 2
    private const val greenQuantizationNum = 2
    private const val blueQuantizationNum = 2

    private var errR = 0
    private var errG = 0
    private var errB = 0

    private var rPalette = IntArray(redQuantizationNum)
    private var gPalette = IntArray(greenQuantizationNum)
    private var bPalette = IntArray(blueQuantizationNum)

    private fun Int.red() = this shr 16 and 0xFF

    private fun Int.green() = this shr 8 and 0xFF
    private fun Int.blue() = this and 0xFF
    private fun Int.getColor(param: Double) = getColor(
        (this.red() + errR * param / 16).toInt(),
        (this.green() + errG * param / 16).toInt(),
        (this.blue() + errB * param / 16).toInt()
    )

    private fun com(neighbourColorComponent: Int) = max(min(neighbourColorComponent, 0xFF), 0)

    private fun getColor(
        neighbourR: Int,
        neighbourG: Int,
        neighbourB: Int
    ) = 0xFF shl 24 or (com(neighbourR) shl 16) or (com(neighbourG) shl 8) or com(neighbourB)

    private fun fillPalette(
        palette: IntArray,
        interval: Int,
        quantum: Int
    ) {
        var color = 0

        for (i in 0 until quantum) {
            palette[i] = color
            color += interval
            if (color > 0xFF) color = 0xFF
        }
    }

    private fun createPalette() {
        rPalette = IntArray(redQuantizationNum)
        gPalette = IntArray(greenQuantizationNum)
        bPalette = IntArray(blueQuantizationNum)

        /* This is the step between colors in palette.
        *  Interval depends on quantization number.
        *  Quantization number is the number of colors in palette.
        */

        fillPalette(rPalette, 256 / (redQuantizationNum - 1), redQuantizationNum)
        fillPalette(gPalette, 256 / (greenQuantizationNum - 1), greenQuantizationNum)
        fillPalette(bPalette, 256 / (blueQuantizationNum - 1), blueQuantizationNum)
    }

    private fun findColorInPalette(palette: IntArray, color: Int): Int {
        var min = 1000
        var pos = 0

        for (i in palette.indices) {
            if (abs(palette[i] - color) < min) {
                min = abs(palette[i] - color)
                pos = i
            }
        }

        return palette[pos]
    }

    private fun right(newImage: PlumImage, x: Int, y: Int) {
        newImage.setRGB(
            x + 1,
            y,
            newImage.getRGB(x + 1, y).getColor(7.0)
        )
    }

    private fun leftDown(newImage: PlumImage, x: Int, y: Int) {
        newImage.setRGB(
            x - 1,
            y + 1,
            newImage.getRGB(x - 1, y + 1).getColor(3.0)
        )
    }

    private fun down(newImage: PlumImage, x: Int, y: Int)  {
        newImage.setRGB(
            x,
            y + 1,
            newImage.getRGB(x, y + 1).getColor(5.0)
        )
    }

    private fun downRight(newImage: PlumImage, x: Int, y: Int) {
        newImage.setRGB(
            x + 1,
            y + 1,
            newImage.getRGB(x + 1, y + 1).getColor(1.0)
        )
    }

    private fun getNewColor(
        newR: Int,
        newG: Int,
        newB: Int
    ) =  0xFF shl 24 or (newR shl 16) or (newG shl 8) or newB

    private fun updateNeighbours(newImage: PlumImage, x: Int, y: Int) {
        if (x < newImage.width - 1) right(newImage, x, y)
        if (x > 0 && y < newImage.height - 1) leftDown(newImage, x, y)
        if (y < newImage.height - 1) down(newImage, x, y)
        if (x < newImage.width - 1 && y < newImage.height - 1) downRight(newImage, x, y)
    }

    private fun updateError(curColor: Int, newColor: Int) {
        errR = curColor.red() - newColor.red()
        errG = curColor.green() - newColor.green()
        errB = curColor.blue() - newColor.blue()
    }

    override fun permit(image: PlumImage): PlumImage {
        val newImage = image.copy()

        createPalette()

        for (x in 0 until newImage.width - 1) {
            for (y in 0 until newImage.height - 1) {
                val curColor = newImage.getRGB(x, y)

                val newColor = getNewColor(
                    findColorInPalette(rPalette, curColor.red()),
                    findColorInPalette(gPalette, curColor.green()),
                    findColorInPalette(bPalette, curColor.blue())
                )

                newImage.setRGB(x, y, newColor)
                updateError(curColor, newColor)
                updateNeighbours(newImage, x, y)
            }
        }

        return newImage
    }
}
