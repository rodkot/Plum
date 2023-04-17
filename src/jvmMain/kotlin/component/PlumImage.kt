package ru.nsu.ccfit.plum.component

import ru.nsu.ccfit.plum.draw.clear
import java.awt.image.BufferedImage

class PlumImage(startWidth: Int, startHeight: Int) :
    BufferedImage(startWidth, startHeight, TYPE_INT_RGB) {

    init {
        clear()
    }

    fun copy(): PlumImage {
        val b = PlumImage(width, height)
        val g = b.graphics
        g.drawImage(this, 0, 0, null)
        g.dispose()
        return b
    }

    fun getPixel(x: Int, y: Int): Triple<Int,Int,Int> {
        val rgb = super.getRGB(x, y)
        val r = rgb shr 16 and 0xFF
        val g = rgb shr 8 and 0xFF
        val b = rgb and 0xFF
        return Triple(r,g,b)
    }

    fun isIn(
    x: Int,
    y: Int,
    i: Int = 0,
    j: Int = 0
    ) = x + i > 0
    && y + j > 0
    && x + i < this.width
    && y + j < this.height
}

fun BufferedImage.toPlumImage(): PlumImage {
    val kivisImage = PlumImage(width, height)
    val g = kivisImage.graphics
    g.drawImage(this, 0, 0, null)
    g.dispose()
    return kivisImage
}