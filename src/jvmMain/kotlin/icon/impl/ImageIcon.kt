package ru.nsu.ccfit.plum.icon.impl

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.nsu.ccfit.plum.icon.Icon

/**
 * Класс отоборажающий иконку из растровой картинки из ресурсов
 */
class ImageIcon(private val path: String) : Icon() {
    @Composable
    override fun render() {
        Image(
            painter = painterResource(path),
            contentDescription = path,
            modifier = Modifier.height(30.dp).width(30.dp)
        )
    }
}