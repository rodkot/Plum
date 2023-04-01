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
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ru.nsu.ccfit.plum.component.*
import ru.nsu.ccfit.plum.dialog.AboutDialog
import ru.nsu.ccfit.plum.dialog.FileOpenDialog
import ru.nsu.ccfit.plum.dialog.FileSaveDialog
import ru.nsu.ccfit.plum.dialog.ManualDialog
import ru.nsu.ccfit.plum.filter.Filter
import ru.nsu.ccfit.plum.filter.SmoothingFilter
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.UIManager

class MainWindowController {
    companion object {
        val currentFilter = mutableStateOf<Filter>(SmoothingFilter)

        var size = mutableStateOf(IntSize.Zero)
        var image = ImageIO.read(Thread.currentThread().contextClassLoader.getResource("test-image.png")).toPlumImage()

        val toolBar = ToolBar(currentFilter)
        val canvas = PaintCanvas()
    }
}


@Composable
@Preview
fun MainWindow() {


    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val dialogAbout = remember { Menu.Controller.about }
        val saveAction = remember { Menu.Controller.save }
        val openAction = remember { Menu.Controller.open }
        val dialogManual = remember { Menu.Controller.instruction }
        val filter = MainWindowController.currentFilter.value

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
                    .horizontalScroll(stateHorizontal)
            ) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

                MainWindowController.image = filter.draw(
                    MainWindowController.image,
                            Offset(0f,0f),
                    Offset(0f,0f),
                            MainWindowController.size.value
                        )

                MainWindowController.canvas.render(MainWindowController.image)

                if (openAction.value) {
                    MainWindowController.canvas.stop()
                    FileOpenDialog {
                        if (it != null) {
                            try {
                                val file = File(it)
                                val image = ImageIO.read(file)
                                MainWindowController.image = image.toPlumImage()
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
                                ImageIO.write(MainWindowController.image, "PNG", output)
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