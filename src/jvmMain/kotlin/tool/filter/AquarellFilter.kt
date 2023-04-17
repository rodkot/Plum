package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.draw.getImageFilter
import java.util.*

object AquarellFilter : Filter("Акварелизация") {

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



    override fun permit(image: PlumImage): PlumImage {
        val newImage = image.copy()

        newImage.getImageFilter { x, y ->
            median(newImage, x, y)
        }

        return SharpnessFilter.permit(newImage)
    }
}