package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.draw.getImageFilter
import kotlin.math.pow
import kotlin.math.roundToInt

object GammaFilter : Filter("Гамма-коррекция") {
    var gamma = 2.2

    override fun permit(image: PlumImage): PlumImage {
        val newImage = image.copy()

        newImage.getImageFilter { x, y ->
            val pixel = newImage.getPixel(x, y)

            getNewColor(
                com(gamma(pixel.first)),
                com(gamma(pixel.second)),
                com(gamma(pixel.third))
            )
        }

        return newImage
    }

    private fun gamma(component: Int) = (255 * (component / 256.0).pow(1 / gamma)).roundToInt()
}