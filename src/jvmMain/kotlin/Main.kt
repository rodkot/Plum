package ru.nsu.ccfit.plum

import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import ru.nsu.ccfit.plum.component.Menu
import java.awt.Dimension

class SizeWindows {
    companion object {
        fun height(): Int = 480
        fun width(): Int = 640
    }
}

fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "Plum",
        icon = painterResource("drawable/launcher_icons/linux.png"),
        state = WindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            width = SizeWindows.width().dp,
            height = SizeWindows.height().dp
        )
    ) {
        window.minimumSize = Dimension(SizeWindows.width(), SizeWindows.height())
        Menu.render(this)
        MainWindow().render()
    }
}