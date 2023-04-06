package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.draw.addBoarderImage
import ru.nsu.ccfit.plum.draw.getImageFilter
import ru.nsu.ccfit.plum.draw.getIntRGB
import ru.nsu.ccfit.plum.draw.getValueFilter
import kotlin.math.exp
import kotlin.math.pow

// TODO По добавлению фильтра
// Нужно добавить имплементацию фильра
object SmoothingFilter : Filter("Cглаживающий фильтр") {
    var sigma: Float = 1.5F
    var size: Int = 3

    /**
     * Функция считает матрицу Гаусса
     * @param n размерность матрицы
     * @param sigma параметр Гаусса
     * @return матрицу Гаусса
     */
    private fun getGaussMatrix(n: Int, sigma: Float): Array<DoubleArray> {
        var sum = 0.0
        val matrix = Array(n) { DoubleArray(n) }
        for (i in 0 until n) {
            for (j in 0 until n) {
                matrix[i][j] = exp(-(i.toDouble().pow(2.0) + j.toDouble().pow(2.0)) / (2 * sigma))
                sum += matrix[i][j]
            }
        }
        for (i in 0 until n) {
            for (j in 0 until n) {
                matrix[i][j] /= sum
            }
        }
        return matrix
    }

    override fun permit(image: PlumImage): PlumImage {
        val newImage = image.copy()
        val matrixFilter = getGaussMatrix(size, sigma)
        val imageBoarder = newImage.addBoarderImage(size)

        newImage.getImageFilter { x, y -> imageBoarder.getValueFilter(matrixFilter, x+ size, y+ size).getIntRGB() }
        return newImage
    }
}