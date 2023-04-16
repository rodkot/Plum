package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.Param
import ru.nsu.ccfit.plum.component.PlumImage
import kotlin.math.abs

object DitheringFilter : Filter("Дизеринг") {
    var redQuantizationNum = 2
    var greenQuantizationNum = 2
    var blueQuantizationNum = 2
    var algorithm = Algorithm.FLOYD_STEINBERG

    var rPalette = IntArray(redQuantizationNum)
    var gPalette = IntArray(greenQuantizationNum)
    var bPalette = IntArray(blueQuantizationNum)

    private fun fillPalette(
        palette: IntArray,
        interval: Int,
        quantum: Int
    ) {
        var color = 0

        for (i in 0 until quantum) {
            palette[i] = color
            color += interval
            if (color > 0xFF) color = 0xFF
        }
    }

    fun createPalette() {
        rPalette = IntArray(redQuantizationNum)
        gPalette = IntArray(greenQuantizationNum)
        bPalette = IntArray(blueQuantizationNum)

        /**
         *     This is the step between colors in palette.
         *     Interval depends on quantization number.
         *     Quantization number is the number of colors in pa
         */

        fillPalette(rPalette, 256 / (redQuantizationNum - 1), redQuantizationNum)
        fillPalette(gPalette, 256 / (greenQuantizationNum - 1), greenQuantizationNum)
        fillPalette(bPalette, 256 / (blueQuantizationNum - 1), blueQuantizationNum)
    }

    fun findColorInPalette(palette: IntArray, color: Int): Int {
        var min = 1000
        var pos = 0

        for (i in palette.indices) {
            if (abs(palette[i] - color) < min) {
                min = abs(palette[i] - color)
                pos = i
            }
        }

        return palette[pos]
    }

    override fun permit(image: PlumImage) = when (algorithm) {
        Algorithm.ORDERED -> OrderedDithering.permit(image)
        Algorithm.FLOYD_STEINBERG -> FloydSteinbergDithering.permit(image)
    }

    enum class Algorithm : Param {
        FLOYD_STEINBERG,
        ORDERED;

        override fun getName() = toString()

        override fun toString() = when (this) {
            FLOYD_STEINBERG -> "Флойд-Стейнберг"
            ORDERED -> "Упорядоченный"
        }
    }
}
