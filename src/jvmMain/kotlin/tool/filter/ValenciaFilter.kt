package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.draw.getImageFilter
import kotlin.math.roundToInt

object ValenciaFilter: Filter("Валенсия") {
    override fun permit(image: PlumImage): PlumImage {
        val newImage = image.copy()

        newImage.getImageFilter { x, y ->
            val pixel = newImage.getPixel(x, y)

            getNewColor(
                com(contrastFun(saturationFunction(overlayFun(pixel.first,255, 0.08), 0.1), 0.05)),
                com(contrastFun(saturationFunction(overlayFun(pixel.second, 225, 0.08), 0.1), 0.05)),
                com(contrastFun(saturationFunction(overlayFun(pixel.third, 80, 0.08), 0.1), 0.05))
            )
        }

        return newImage
    }

    private fun overlayFun(component: Int, color: Int, scale: Double) = com(component - ((component - color) * scale).roundToInt())
    private fun contrastFun(color: Int, p: Double) = com(((color - 128) * factor(p) + 128).toInt())
    private fun saturationFunction(color: Int, p: Double) = com((color + 255 * p).toInt())

    private fun factor(p: Double): Double{
        val adj = p * 255

        return (259 * (adj + 255)) / (255 * (259 - adj))
    }
}