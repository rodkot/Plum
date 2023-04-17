package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.draw.getImageFilter

object SharpnessFilter: Filter("Резкость") {
    private var resR = 0
    private var resG = 0
    private var resB = 0

    private val kernel = arrayOf(
        intArrayOf(0, -1, 0),
        intArrayOf(-1, 5, -1),
        intArrayOf(0, -1, 0)
    )

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

    private fun resColorInit() {
        resR = 0
        resG = 0
        resB = 0
    }

    override fun permit(image: PlumImage): PlumImage {
        val newImage = image.copy()

        newImage.getImageFilter { x, y ->
            resColorInit()
            image.sharpness(x, y)
            getNewColor(
                com(resR),
                com(resG),
                com(resB)
            )
        }

        return newImage
    }
}