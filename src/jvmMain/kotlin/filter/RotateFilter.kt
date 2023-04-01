package ru.nsu.ccfit.plum.filter

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import ru.nsu.ccfit.plum.component.PlumImage
import java.awt.Point


object RotateFilter : Filter("Фильтр поворота") {
    /**
     * @param angle угол поворота
     */
    var angle: Int = 45

    private var minX = 0
    private var maxX = 0
    private var minY = 0
    private var maxY = 0

    private fun getRotatedPixelMap(
        original: PlumImage,
        height: Int,
        width: Int,
        cos: Double,
        sin: Double
    ) = Array(height * width) { i ->
            val x = i % width
            val y = i / width

            val newX = (x * cos - y * sin).toInt()
            val newY = (x * sin + y * cos).toInt()

            if (i == 0) {
                maxX = newX
                maxY = newY
                minX = newY
                minY = newY
            } else if (newX > maxX) {
                maxX = newX
            } else if (newX < minX) {
                minX = newX
            } else if (newY < minY) {
                minY = newY
            } else if (newY > maxY) {
                maxY = newY
            }

            Pair(
                Point(
                    newX,
                    newY
                ),
                original.getRGB(x, y)
            )
        }

    private fun rotateImage(original: PlumImage): PlumImage {
        val radians = Math.toRadians(angle.toDouble())
        val cos = Math.cos(radians)
        val sin = Math.sin(radians)
        val pixelMap = getRotatedPixelMap(original, original.height, original.width, cos, sin)
        val newImage = PlumImage(maxX - minX + 1, maxY - minY + 1)

        for (pixel in pixelMap) {
            newImage.setRGB(pixel.first.x - minX, pixel.first.y - minY, pixel.second)
        }

        return newImage
    }

    /**
     * Матрица поворота
     * /cos A -sin A \
     * \sin A  cos A /
     */
    override fun draw(image: PlumImage, pressOffset: Offset, releaseOffset: Offset, size: IntSize) = rotateImage(image)
}