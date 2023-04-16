package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import java.util.*

object AquarellFilter : Filter("Акварелизация") {
    private val kernel = arrayOf(
        intArrayOf(0, -1, 0),
        intArrayOf(-1, 5, -1),
        intArrayOf(0, -1, 0)
    )

    private fun median(image: PlumImage, curX: Int, curY: Int): Int {
        val redArr = IntArray(25)
        val greenArr = IntArray(25)
        val blueArr = IntArray(25)
        var i = 0

        for (x in curX - 2..curX + 2) {
            for (y in curY - 2..curY + 2) {
                if (image.isIn(x, y)) {
                    val color = image.getRGB(x, y)

                    redArr[i] = color.red()
                    greenArr[i] = color.green()
                    blueArr[i] = color.blue()

                    i++
                }
            }
        }

        Arrays.sort(redArr)
        Arrays.sort(greenArr)
        Arrays.sort(blueArr)
        /**
         * Taking median color
         */
        return getNewColor(
            redArr[12],
            greenArr[12],
            blueArr[12]
        )
    }

    private fun PlumImage.isIn(
        x: Int,
        y: Int,
        i: Int = 0,
        j: Int = 0
    ) = x + i > 0
            && y + j > 0
            && x + i < this.width
            && y + j < this.height

    private var resR = 0
    private var resG = 0
    private var resB = 0

    private fun resColorInit() {
        resR = 0
        resG = 0
        resB = 0
    }

    private fun PlumImage.sharpness(x: Int, y: Int) {
        for (i in -1..1) {
            for (j in -1..1) {
                val curColor = if (this.isIn(x, y, i, j)) {
                    this.getRGB(x + i, y + j)
                } else {
                    this.getRGB(x, y)
                }

                resR += curColor.red() * kernel[i + 1][j + 1]
                resG += curColor.green() * kernel[i + 1][j + 1]
                resB += curColor.blue() * kernel[i + 1][j + 1]
            }
        }
    }

    private fun PlumImage.setNewColor(x: Int, y: Int) {
        this.setRGB(
            x,
            y,
            getNewColor(
                com(resR),
                com(resG),
                com(resB)
            )
        )
    }

    override fun permit(image: PlumImage): PlumImage {
        val newImage = image.copy()

        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                newImage.setRGB(x, y, median(newImage, x, y))
            }
        }

        val newImage1 = newImage.copy()

        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                resColorInit()
                newImage.sharpness(x, y)
                newImage1.setNewColor(x, y)
            }
        }

        return newImage1
    }
}