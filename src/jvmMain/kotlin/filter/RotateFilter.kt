package ru.nsu.ccfit.plum.filter

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import ru.nsu.ccfit.plum.component.PlumImage
import java.awt.Point
import java.util.concurrent.TimeUnit


object RotateFilter : Filter("Фильтр поворота") {
    var minX = 0
    var maxX = 0
    var minY = 0
    var maxY = 0

    /** Матрица поворота
     * /cos A -sin A \
     * \sin A  cos A /
     */
    override fun draw(image: PlumImage, pressOffset: Offset, releaseOffset: Offset, size: IntSize) = rotateImage(image)

    private fun rotateImage(original: PlumImage, angle: Int = 45): PlumImage {
        val radians = Math.toRadians(angle.toDouble())
        val cos = Math.cos(radians)
        val sin = Math.sin(radians)
        val pixelMap = getPixelMap(original, original.height, original.width, cos, sin)
        val newImage = PlumImage(maxX - minX + 1, maxY - minY + 1)

        for (it in pixelMap) {
            newImage.setRGB(it.first.x - minX, it.first.y - minY, it.second)
        }

        return newImage
    }

    private fun getPixelMap(original: PlumImage, height: Int, width: Int, cos: Double, sin: Double) =
        Array(height * width) { i ->
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
}