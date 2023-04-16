package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sin


object RotateFilter : Filter("Фильтр поворота") {
    /**
     * @param angle угол поворота
     */
    var angle: Int = 45

    private fun rotateImage(original: PlumImage): PlumImage {
        val radians = Math.toRadians(angle.toDouble())
        val cos = cos(radians)
        val sin = sin(radians)

        /**
         * We know previous picture angeles, so getting their new
         * coordinates allow us to know limits of new image
         */
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
                /**
                 * Move picture to its center
                 */
                val yCenter = y - newImg.height / 2
                val xCenter = x - newImg.width / 2

                /**
                 * Getting x and y dimension of angle that out of the frame
                 */
                val xOutAngle = (newImg.width - original.width).absoluteValue / 2
                val yOutAngle = (newImg.height - original.height).absoluteValue / 2

                /**
                 * Calculation of color coordinate
                 */
                val newX = (yCenter * sin + xCenter * cos).toInt() + newImg.width / 2 - xOutAngle
                val newY = (yCenter * cos - xCenter * sin).toInt() + newImg.height / 2 - yOutAngle

                val color = if (
                    newX > 0
                    && newY > 0
                    && newX < original.width
                    && newY < original.height
                ) original.getRGB(
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
     * Rotation matrix
     * /cos A -sin A \
     * \sin A  cos A /
     */
    override fun permit(image: PlumImage): PlumImage = rotateImage(image)
}