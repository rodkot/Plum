package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import java.awt.Point
import kotlin.math.absoluteValue


object RotateFilter : Filter("Фильтр поворота") {
    /**
     * @param angle угол поворота
     */
    var angle: Int = 45

    private var minX = 0
    private var maxX = 0
    private var minY = 0
    private var maxY = 0

    private fun rotateImage(original: PlumImage): PlumImage {
        val radians = Math.toRadians(angle.toDouble())
        val cos = Math.cos(radians)
        val sin = Math.sin(radians)
        val height = (original.width * cos).toInt()
        val newImg = PlumImage(
            with(
                listOf(
                    cos * original.width,
                    cos * original.width - sin * original.height,
                    -sin * original.height,
                    0.0
                )
            ) {
                max().toInt() - min().toInt()
            },
            with(
                listOf(
                    sin * original.width,
                    sin * original.width + cos * original.height,
                    cos * original.height,
                    0.0
                )
            ) {
                max().toInt() - min().toInt()
            }
        )


        for (x in 0 until newImg.width)
            for (y in 0 until newImg.height) {
                val newX = ((y - newImg.height / 2)* sin + (x - newImg.width / 2) * cos).toInt() + newImg.width / 2 - (newImg.width - original.width).absoluteValue / 2
                val newY = ((y - newImg.height / 2)* cos - (x - newImg.width / 2) * sin).toInt() + newImg.height / 2 - (newImg.height - original.height).absoluteValue / 2

                val color = if ( newX > 0
                    && newY > 0
                    && newX < original.width
                    && newY < original.height) original.getRGB(
                    newX,
                    newY
                ) else -1

                newImg.setRGB(
                    x,
                    y,
                    color
                )
            }

        return newImg
    }

    /**
     * Матрица поворота
     * /cos A -sin A \
     * \sin A  cos A /
     */
    override fun permit(image: PlumImage): PlumImage = rotateImage(image)
}