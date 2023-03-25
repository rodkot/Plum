package ru.nsu.ccfit.plum

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ru.nsu.ccfit.plum.component.*
import ru.nsu.ccfit.plum.dialog.AboutDialog
import ru.nsu.ccfit.plum.dialog.FileOpenDialog
import ru.nsu.ccfit.plum.dialog.FileSaveDialog
import ru.nsu.ccfit.plum.dialog.ManualDialog
import ru.nsu.ccfit.plum.draw.resize
import ru.nsu.ccfit.plum.filter.Filter
import ru.nsu.ccfit.plum.filter.SmoothingFilter
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

class MainWindowController {
    companion object {
        val currentFilter = mutableStateOf<Filter>(SmoothingFilter())

        var size = mutableStateOf(IntSize.Zero)
        val image = mutableStateOf(PlumImage(700, 400))

        val toolBar = ToolBar(currentFilter)
        val canvas = PaintCanvas()
    }
}


@Composable
@Preview
fun MainWindow() {

    val s = MainWindowController.currentFilter.value
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val dialogAbout = remember { Menu.Controller.about }
        val saveAction = remember { Menu.Controller.save }
        val openAction = remember { Menu.Controller.open }
        val dialogManual = remember { Menu.Controller.instruction }
        val remImage = remember { MainWindowController.image }
        Menu.Controller.filter = MainWindowController.currentFilter

        Box(Modifier.fillMaxWidth()) {
            MainWindowController.toolBar.render()
        }

        Box(Modifier.fillMaxSize().background(Color.Gray)) {
            val stateVertical = rememberScrollState(0)
            val stateHorizontal = rememberScrollState(0)

            Box(
                modifier = Modifier
                    .fillMaxSize().padding(13.dp)
                    .verticalScroll(stateVertical)
                    .horizontalScroll(stateHorizontal).onSizeChanged {
                        if (MainWindowController.size.value == IntSize.Zero)
                            MainWindowController.image.value = MainWindowController.image.value.resize(it)
                        MainWindowController.size.value = it
                        println("{main win $it}")
                    }
            ) {

                MainWindowController.canvas.render(remImage.value) { image: PlumImage, press: Offset, release: Offset ->
                    run {
                        remImage.value = s.draw(
                            image,
                            press,
                            release,
                            MainWindowController.size.value
                        )
                    }
                }

                if (openAction.value) {
                    MainWindowController.canvas.stop()
                    FileOpenDialog {
                        if (it != null) {
                            try {
                                val file = File(it)
                                val image = ImageIO.read(file)
                                remImage.value = image.toPlumImage()
                                openAction.value = false
                                MainWindowController.canvas.start()
                            } catch (e: Exception) {
                                openAction.value = false
                                MainWindowController.canvas.start()
                            }
                        }
                    }
                }

                if (saveAction.value) {
                    FileSaveDialog {
                        if (it != null) {
                            val output = File(it.plus(".png"))
                            output.createNewFile()

                            try {
                                ImageIO.write(MainWindowController.image.value, "PNG", output)
                            } catch (e: IOException) {
                                println(e.message)
                            }
                        }
                        saveAction.value = false
                    }
                }

                if (dialogAbout.value) {
                    AboutDialog { dialogAbout.value = false }
                }

                if (dialogManual.value) {
                    ManualDialog { dialogManual.value = false }
                }
            }

            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd)
                    .fillMaxHeight(),
                adapter = rememberScrollbarAdapter(stateVertical),
                style = ru.nsu.ccfit.plum.component.defaultScrollbarStyle()
            )
            HorizontalScrollbar(
                modifier = Modifier.align(Alignment.BottomStart)
                    .fillMaxWidth(),
                style = ru.nsu.ccfit.plum.component.defaultScrollbarStyle(),
                adapter = rememberScrollbarAdapter(stateHorizontal)
            )
        }
    }
}