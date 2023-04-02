package ru.nsu.ccfit.plum

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

class MainWindow : Renderable {

    private val currentFilter = mutableStateOf<Filter>(SmoothingFilter)
    private val originalMode = mutableStateOf(false)

    private var size = mutableStateOf(IntSize.Zero)
    private var originalImage =
        ImageIO.read(Thread.currentThread().contextClassLoader.getResource("test-image.png")).toPlumImage()
    private var currentImage = originalImage

    private val toolBar = ToolBar(currentFilter)
    private val canvas = PaintCanvas()


    @Composable
    override fun render() {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val filter = currentFilter.value

            Menu.Controller.filter = currentFilter

            Box(Modifier.fillMaxWidth()) {
                toolBar.render()
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


                    //Приминение фильтра
                    currentImage = filter.draw(
                        originalImage,
                        Offset(0f, 0f),
                        Offset(0f, 0f),
                        size.value
                    )

                    drawImage()

                    openDialog()

                    saveDialog()

                    manualDialog()

                    aboutDialog()
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

    @Composable
    fun openDialog() {
        val openAction = remember { Menu.Controller.open }
        if (openAction.value) {

            FileOpenDialog {
                if (it != null) {
                    try {
                        val file = File(it)
                        val image = ImageIO.read(file)
                        originalImage = image.toPlumImage()
                        currentImage = originalImage
                        originalMode.value = !originalMode.value
                        openAction.value = false

                    } catch (e: Exception) {
                        openAction.value = false

                    }
                }
            }
        }
    }

    @Composable
    fun manualDialog() {
        val dialogManual = remember { Menu.Controller.instruction }
        if (dialogManual.value) {
            ManualDialog { dialogManual.value = false }
        }
    }

    @Composable
    fun aboutDialog() {
        val dialogAbout = remember { Menu.Controller.about }
        if (dialogAbout.value) {
            AboutDialog { dialogAbout.value = false }
        }
    }

    @Composable
    fun saveDialog() {
        val saveAction = remember { Menu.Controller.save }
        if (saveAction.value) {
            FileSaveDialog {
                if (it != null) {
                    val output = File(it.plus(".png"))
                    output.createNewFile()

                    try {
                        ImageIO.write(currentImage, "PNG", output)
                    } catch (e: IOException) {
                        println(e.message)
                    }
                }
                saveAction.value = false
            }
        }
    }

    @Composable
    fun drawImage() {
        //Определение какое изображение должно отображаться
        val image =
            if (originalMode.value) originalImage else currentImage


        canvas.render(image) {
            originalMode.value = !originalMode.value
        }
    }
}


