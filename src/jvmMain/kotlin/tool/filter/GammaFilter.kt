package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.draw.getImageFilter
import kotlin.math.pow

object GammaFilter : Filter("Гамма-коррекция") {
    var gamma = 2.2f

    override fun permit(image: PlumImage): PlumImage {
        val newImage = image.copy()

        newImage.getImageFilter { x, y ->
            val pixel = newImage.getPixel(x, y)

            getNewColor(
                com(pixel.first.toFloat().pow(1 / gamma).toInt()),
                com(pixel.second.toFloat().pow(1 / gamma).toInt()),
                com(pixel.third.toFloat().pow(1 / gamma).toInt())
            )
        }


        return newImage
    }
}