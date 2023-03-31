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
}

fun BufferedImage.toPlumImage(): PlumImage {
    val kivisImage = PlumImage(width, height)
    val g = kivisImage.graphics
    g.drawImage(this, 0, 0, null)
    g.dispose()
    return kivisImage
}