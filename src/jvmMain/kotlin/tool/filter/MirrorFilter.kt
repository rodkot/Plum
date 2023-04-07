package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.Param
import ru.nsu.ccfit.plum.component.PlumImage


object MirrorFilter : Filter("Отражение") {
    var alignment = Alignment.VERTICAL

    override fun permit(image: PlumImage): PlumImage {
        val newImage = image.copy()

        when (alignment) {
            Alignment.VERTICAL -> reflection(newImage, image, Alignment.VERTICAL)
            Alignment.HORIZONTAL -> reflection(newImage, image, Alignment.HORIZONTAL)
        }

        return newImage
    }

    private fun reflection(newImage: PlumImage, image: PlumImage, alignment: Alignment) {
        val height = image.height
        val width = image.width

        for (y in 0 until height) {
            for (x in 0 until width) {
                newImage.setRGB(x, y, alignmentReflection(image, x, y, alignment))
            }
        }
    }

    private fun alignmentReflection(
        image: PlumImage,
        x: Int,
        y: Int,
        alignment: Alignment
    ) = when(alignment) {
            Alignment.HORIZONTAL -> image.getRGB(x, image.height - y - 1)
            Alignment.VERTICAL -> image.getRGB(image.width - x - 1, y)
        }

    enum class Alignment: Param {
        VERTICAL,
        HORIZONTAL;

        override fun getName(): String {
            return toString()
        }
    }
}