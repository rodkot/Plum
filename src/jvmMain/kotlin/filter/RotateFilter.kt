package ru.nsu.ccfit.plum.filter

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.draw.addBoarderImage
import ru.nsu.ccfit.plum.draw.getImageFilter
import ru.nsu.ccfit.plum.draw.getValueFilter
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import kotlin.math.exp
import kotlin.math.pow


object RotateFilter : Filter("Фильтр поворота") {
    /** Матрица поворота
     * /cos A -sin A \
     * \sin A  cos A /
     */
    override fun draw(image: PlumImage, pressOffset: Offset, releaseOffset: Offset, size: IntSize) {
        rotateImage(image)
    }

    private fun rotateImage(image: BufferedImage, angle: Int = 45) {
        val radians = Math.toRadians(angle.toDouble())
        val cos = Math.cos(radians)
        val sin = Math.sin(radians)

        for (y in 0 until image.height)
            for (x in 0 until image.width) {
                val color = image.getRGB(x, y)
                val newX = x * cos - y * sin
                val newY = x * sin + y * cos
                image.setRGB(newX.toInt(), newY.toInt(), color)
            }
    }
}