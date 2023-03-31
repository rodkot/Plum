package ru.nsu.ccfit.plum.filter

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import ru.nsu.ccfit.plum.component.PlumImage
import ru.nsu.ccfit.plum.draw.addBoarderImage
import ru.nsu.ccfit.plum.draw.getImageFilter
import ru.nsu.ccfit.plum.draw.getValueFilter
import kotlin.math.exp
import kotlin.math.pow

// TODO По добавлению фильтра
// Нужно добавить имплементацию фильра
object SmoothingFilter : Filter("Cглаживающий фильтр") {
    var sigma: Float = 1.5F
    var n: Int = 10

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

    override fun draw(image: PlumImage, pressOffset: Offset, releaseOffset: Offset, size: IntSize) {
        val matrixFilter = getGaussMatrix(n, sigma)
        val imageBoarder = image.addBoarderImage(n)

        image.getImageFilter { x, y -> imageBoarder.getValueFilter(matrixFilter, x, y) }
    }
}