package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.Param
import ru.nsu.ccfit.plum.component.PlumImage


object MirrorFilter : Filter("Отражение") {
    var alignment = Alignment.VERTICAL

    override fun permit(image: PlumImage): PlumImage {
        val newImage = image.copy()

        when (alignment) {
            Alignment.VERTICAL -> verticalReflection(newImage, image)
            Alignment.HORIZONTAL -> horizontalReflection(newImage, image)
        }

        return newImage
    }

    private fun horizontalReflection(newImage: PlumImage, image: PlumImage) {
        val height = image.height
        val width = image.width

        for (y in 0 until height) {
            for (x in 0 until width) {
                newImage.setRGB(x, y, image.getRGB(x, height - y - 1))
            }
        }
    }

    private fun verticalReflection(newImage: PlumImage, image: PlumImage) {
        val height = image.height
        val width = image.width

        for (y in 0 until height) {
            for (x in 0 until width) {
                newImage.setRGB(x, y, image.getRGB(width - x - 1, y))
            }
        }
    }

    enum class Alignment: Param {
        VERTICAL,
        HORIZONTAL;

        override fun getName(): String {
            return toString()
        }
    }
}