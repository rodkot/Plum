package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import java.awt.Color
import java.awt.image.BufferedImage


object BleachBypassFilter : Filter("Тиснение") {
    private val amount = 0.5f

    private fun bleachBypass(image: PlumImage, amount: Float): PlumImage {
        val result = PlumImage(image.width, image.height)

        // Iterate over each pixel in the image
        for (y in 0 until image.height) {
            for (x in 0 until image.width) {
                // Get the color of the current pixel
                val color = Color(image.getRGB(x, y))

                // Calculate the grayscale value of the pixel
                val gray: Int = (color.red + color.green + color.blue) / 3

                // Apply the bleach bypass effect by blending the grayscale value with the original color
                val r = (amount * color.red + (1 - amount) * gray).toInt()
                val g = (amount * color.green + (1 - amount) * gray).toInt()
                val b = (amount * color.blue + (1 - amount) * gray).toInt()

                // Create a new color with the blended RGB values
                val resultColor = Color(r, g, b)

                // Set the corresponding pixel in the result image to the new color
                result.setRGB(x, y, resultColor.rgb)
            }
        }
        return result
    }


    override fun permit(image: PlumImage): PlumImage {

        return bleachBypass(image, amount)
    }
}