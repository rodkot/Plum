package ru.nsu.ccfit.plum

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.nsu.ccfit.plum.component.*
import ru.nsu.ccfit.plum.dialog.impl.AboutDialog
import ru.nsu.ccfit.plum.dialog.impl.FileOpenDialog
import ru.nsu.ccfit.plum.dialog.impl.FileSaveDialog
import ru.nsu.ccfit.plum.dialog.impl.ManualDialog
import ru.nsu.ccfit.plum.tool.filter.Filter
import ru.nsu.ccfit.plum.tool.filter.SmoothingFilter
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.UIManager
import kotlin.math.max
import kotlin.math.min

class MainWindow : Renderable {

    private val currentFilter = mutableStateOf<Filter>(SmoothingFilter)
    private val originalMode = mutableStateOf(false)
    private val interpolationMode = mutableStateOf(false)

    private var size = mutableStateOf(IntSize(100, 100))
    private var originalImage =
        ImageIO.read(Thread.currentThread().contextClassLoader.getResource("drawable/test-image.jpg")).toPlumImage()
    private var currentImage = originalImage

    private val toolBar = ToolBar(currentFilter, interpolationMode)
    private val canvas = PaintCanvas()
    private var xStart = 0
    private var yStart = 0
    private var horizontalStart = 0
    private var verticalStart = 0
    private var xFinish = 0
    private var yFinish = 0


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun render() {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val filter = currentFilter.value

            Menu.filter = currentFilter
            Menu.interpolation = interpolationMode

            Box(Modifier.fillMaxWidth()) {
                toolBar.render()
            }

            Box(Modifier.fillMaxSize().background(Color.Gray)
                //TODO: Кастыль
                // Чтобы отображение размера было прабильное, отнимается размер сколлов
                .onSizeChanged {
                    size.value = IntSize(it.width - 42, it.height - 42)
                }) {
                val stateVertical = rememberScrollState(0)
                val stateHorizontal = rememberScrollState(0)


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(13.dp)
                        .verticalScroll(stateVertical)
                        .horizontalScroll(stateHorizontal)
                        .onDrag(
                            onDragStart = {
                                verticalStart = stateVertical.value
                                horizontalStart = stateHorizontal.value
                            },
                            onDrag = {
                                horizontalStart -= it.x.toInt()
                                verticalStart -= it.y.toInt()

                                runBlocking {
                                    stateVertical.scrollTo(verticalStart)
                                    stateHorizontal.scrollTo(horizontalStart)
                                }
                            }
                        )
                ) {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

                    //TODO: Кастыль для работы применения фильтра при изменении его параметров
                    (filter.check.value)
                    //Приминение фильтра
                    currentImage = filter.permit(
                        originalImage
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
        val openAction = remember { Menu.open }
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
        val dialogManual = remember { Menu.instruction }
        if (dialogManual.value) {
            ManualDialog { dialogManual.value = false }
        }
    }

    @Composable
    fun aboutDialog() {
        val dialogAbout = remember { Menu.about }
        if (dialogAbout.value) {
            AboutDialog { dialogAbout.value = false }
        }
    }

    @Composable
    fun saveDialog() {
        val saveAction = remember { Menu.save }
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

        canvas.render(image, interpolationMode.value, size.value) {
            originalMode.value = !originalMode.value
        }
    }
}


