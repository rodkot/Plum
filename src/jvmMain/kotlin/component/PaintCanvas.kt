package ru.nsu.ccfit.plum.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.onClick
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import java.awt.BasicStroke
import java.awt.Color

class PaintCanvas() {

    private val borderWidth = 5
    private val hatchSpacing = 3
    private val hatchColor = Color.BLACK

    /**
     * Функция возвращает изображение с добавленной границей
     * @param borderWidth ширина штрихов
     * @param hatchColor цвет штриха
     * @param hatchSpacing длина пропусков
     *
     * @return изображение с границей
     */
    private fun PlumImage.addBoarder(borderWidth: Int, hatchSpacing: Int, hatchColor: Color): PlumImage {
        val newImage = PlumImage(width + 2 * borderWidth, height + 2 * borderWidth)
        val g = newImage.createGraphics()
        g.color = Color(0, 0, 0, 0)
        g.fillRect(0, 0, newImage.width, newImage.height)
        g.drawImage(this, borderWidth, borderWidth, null)


        val oldStroke = g.stroke
        g.stroke = BasicStroke(
            borderWidth.toFloat(),
            BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER,
            borderWidth.toFloat(),
            floatArrayOf(hatchSpacing.toFloat(), hatchSpacing.toFloat()),
            0f
        )
        g.color = hatchColor
        g.drawRect(
            borderWidth / 2,
            borderWidth / 2,
            width + borderWidth - 1,
            height + borderWidth - 1
        )
        g.stroke = oldStroke
        g.dispose()
        return newImage
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun render(image: PlumImage, onClick: () -> Unit) {
        Box(Modifier.fillMaxSize().onClick { onClick.invoke() }) {
            Image(
                image.addBoarder(borderWidth, hatchSpacing, hatchColor).toComposeImageBitmap(),
                contentDescription = "Изображение",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}