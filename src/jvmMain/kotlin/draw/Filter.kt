package ru.nsu.ccfit.plum.draw

import ru.nsu.ccfit.plum.component.PlumImage

/**
 * Функция приминяет функцию к каждому пикселю изображения
 * @param getNewColorPixel Фунция которая применяется к каждому пикселю (x,y)
 */
fun PlumImage.getImageFilter(getNewColorPixel: (x: Int, y: Int) -> Int) {
    for (x in 0 until width) {
        for (y in 0 until height) {
            setRGB(
                x,
                y,
                getNewColorPixel.invoke(x, y)
            )
        }
    }
}

/**
 * Фунция вычисления итого цвета по фильру
 *
 * @param matrixFilter Матрица фильра
 * @param x координата изначального изображения
 * @param y координата изначального изображения
 * @return целевой цвет для данной координаты
 */
fun PlumImage.getValueFilter(matrixFilter: Array<DoubleArray>, x: Int, y: Int): Triple<Int, Int, Int> {
    var resultRed = 0.0
    var resultBlue = 0.0
    var resultGreen = 0.0
    for (i in matrixFilter.indices) {
        for (j in matrixFilter.indices) {
            val (red, green, blue) = getPixel(x + i, y + j)

            resultRed += red * matrixFilter[i][j]
            resultBlue += blue * matrixFilter[i][j]
            resultGreen += green * matrixFilter[i][j]
        }
    }
    return Triple(resultRed.toInt(), resultGreen.toInt(), resultBlue.toInt())
}


/**
 * Фунция вычисления итого цвета по фильру
 *
 * @param matrixFilter Матрица фильра
 * @param x координата изначального изображения
 * @param y координата изначального изображения
 * @return целевой цвет для данной координаты
 */
fun PlumImage.getValueFilter(matrixFilter: Array<IntArray>, x: Int, y: Int): Triple<Int, Int, Int> {

    return getValueFilter(matrixFilter.map { it -> it.map { it.toDouble() }.toDoubleArray() }.toTypedArray(), x, y)
}

fun  Triple<Int,Int,Int>.getIntRGB():Int{
    return (this.first shl 16) or (this.second shl 8) or this.third
}

fun  getIntRGB(red:Int,green:Int,blue:Int):Int{
    return (red shl 16) or (green shl 8) or blue
}

/**
 * Функция добавляет симметричную границу
 *
 * @param image Исходное изображение
 * @param n     Ширина границы
 * @return Изображение с границами
 */
fun PlumImage.addBoarderImage(n: Int): PlumImage {
    val newImage = PlumImage(width + 2 * n, height + 2 * n)
    val g2d = newImage.createGraphics()
    g2d.drawImage(this, n, n, width, height, null)
    g2d.dispose()
    for (i in 0 until width) {
        for (j in 0 until n) {
            newImage.setRGB(i + n, j, getRGB(i, n - j))
        }
        for (j in 1..n) {
            newImage.setRGB(i + n, newImage.height - j, getRGB(i, height - n + j - 1))
        }
    }
    for (i in 0 until newImage.height) {
        for (j in 0 until n) {
            newImage.setRGB(j, i, newImage.getRGB(2 * n - j, i))
        }
        for (j in 0 until n) {
            newImage.setRGB(newImage.width - j - 1, i, newImage.getRGB(width + j, i))
        }
    }
    return newImage
}