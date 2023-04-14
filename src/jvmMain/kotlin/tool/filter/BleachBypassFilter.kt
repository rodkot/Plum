package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import java.awt.Color

object BleachBypassFilter : Filter("Удержание серебра") {
    private const val amount = 0.5f

    private fun bleachBypass(image: PlumImage, amount: Float): PlumImage {
        val result = PlumImage(image.width, image.height)

        for (y in 0 until image.height) {
            for (x in 0 until image.width) {

                val color = Color(image.getRGB(x, y))

                val gray: Int = (color.red + color.green + color.blue) / 3

                val r = (amount * color.red + (1 - amount) * gray).toInt()
                val g = (amount * color.green + (1 - amount) * gray).toInt()
                val b = (amount * color.blue + (1 - amount) * gray).toInt()

                val resultColor = Color(r, g, b)

                result.setRGB(x, y, resultColor.rgb)
            }
        }
        return result
    }


    override fun permit(image: PlumImage): PlumImage {
        return bleachBypass(image, amount)
    }
}