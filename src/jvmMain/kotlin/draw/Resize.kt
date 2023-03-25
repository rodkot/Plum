package ru.nsu.ccfit.plum.draw


import androidx.compose.ui.unit.IntSize
import ru.nsu.ccfit.plum.component.PlumImage
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

fun PlumImage.resize(size:IntSize): PlumImage {
    var (w, h) = size.width to size.height
    if (size.width > width) {
        w = size.width
    }
    if (size.height > height) {
        h = size.height
    }
    val resizeImage = PlumImage(w, h)
    resizeImage.clear()
    resizeImage.drawImage(this,size)
    return resizeImage
}

fun PlumImage.drawImage(bufferedImage: BufferedImage,size: IntSize) {
    val g: Graphics2D = createGraphics()
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

    g.drawImage(bufferedImage, 0, 0, size.width, size.height, null)
}