package ru.nsu.ccfit.plum.tool

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import ru.nsu.ccfit.plum.component.Param
import ru.nsu.ccfit.plum.component.PlumImage
import java.awt.Graphics2D
import java.awt.RenderingHints

object InterpolationTool: Tool("Интерполяция") {
    var interpolation = Interpolation.BILINEAR

    enum class Interpolation :Param{
        /*
        Этот режим использует ближайшего соседа пикселя для его интерполяции.
        Он самый быстрый, но обеспечивает самое низкое качество изображения
         */
        NEAREST_NEIGHBOR,

        /*
        Этот режим использует средневзвешенное значение четырех ближайших соседних пикселей для интерполяции нового пикселя.
        Это обеспечивает более высокое качество изображения, чем интерполяция ближайшего соседа, но может быть медленнее.
         */
        BILINEAR,

        /*
        Этот режим использует средневзвешенное значение шестнадцати ближайших соседних пикселей для интерполяции нового пикселя.
        Он обеспечивает высочайшее качество изображения, но является самым медленным
         */
        BICUBIC ;
        override fun getName(): String {
            return toString()
        }
    }

    /**
     * Функция вписывает размеры изображения в прямоугольник с сохранением соотношения сторон изображения
     * @param sizeImage размеры изображения
     * @param sizeRectangle размеры многоугольника
     * @return размер вписанного изображения
     */
    private fun enterImageIntoRectangle(sizeRectangle: IntSize, sizeImage: IntSize): IntSize {
        val rectangleWidth = sizeRectangle.width
        val rectangleHeight = sizeRectangle.height
        val rectangleAspectRatio = rectangleWidth.toDouble() / rectangleHeight

        var imageWidth = sizeImage.width
        var imageHeight = sizeImage.height
        val imageAspectRatio = imageWidth.toDouble() / imageHeight

        if (imageAspectRatio >= rectangleAspectRatio) {
            imageWidth = rectangleWidth
            imageHeight = ((imageWidth / imageAspectRatio).toInt())
        } else {
            imageHeight = rectangleHeight
            imageWidth = ((imageHeight * imageAspectRatio).toInt())
        }

        return IntSize(imageWidth, imageHeight)
    }


    private fun PlumImage.interpolation(interpolation: Interpolation, size: IntSize): PlumImage {
        val newSize = enterImageIntoRectangle(size, IntSize(width, height))
        val image = PlumImage(newSize.width, newSize.height)
        val g2d: Graphics2D = image.createGraphics()

        when (interpolation) {
            Interpolation.NEAREST_NEIGHBOR ->
                g2d.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
                )

            Interpolation.BILINEAR ->
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)

            Interpolation.BICUBIC ->
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC)
        }

        g2d.drawImage(this, 0, 0, newSize.width, newSize.height, null);
        return image
    }

    override fun draw(image: PlumImage, pressOffset: Offset, releaseOffset: Offset, size: IntSize): PlumImage {
        return image.interpolation(interpolation,size)
    }
}