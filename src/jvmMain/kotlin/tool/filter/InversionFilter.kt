package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage

object InversionFilter: Filter("Фильтр инверсии") {
    override fun permit(image: PlumImage): PlumImage {
        val newImg = PlumImage(image.width, image.height)

        for (x in 0 until image.width)
            for (y in 0 until image.height) {
                newImg.setRGB(x, y, -image.getRGB(x, y))
            }

        return newImg
    }
}