package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import kotlin.math.max
import kotlin.math.min
import ru.nsu.ccfit.plum.tool.filter.DitheringFilter as DF

object FloydSteinbergDithering {

    private var errR = 0
    private var errG = 0
    private var errB = 0


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

    private fun down(newImage: PlumImage, x: Int, y: Int) {
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

    fun permit(image: PlumImage): PlumImage {
        val newImage = image.copy()

        DF.createPalette()

        for (x in 0 until newImage.width - 1) {
            for (y in 0 until newImage.height - 1) {
                val curColor = newImage.getRGB(x, y)

                val newColor = getNewColor(
                    DF.findColorInPalette(DF.rPalette, curColor.red()),
                    DF.findColorInPalette(DF.gPalette, curColor.green()),
                    DF.findColorInPalette(DF.bPalette, curColor.blue())
                )

                newImage.setRGB(x, y, newColor)
                updateError(curColor, newColor)
                updateNeighbours(newImage, x, y)
            }
        }

        return newImage
    }
}