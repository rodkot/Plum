package ru.nsu.ccfit.plum

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
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

        var offset = remember { mutableStateOf(IntOffset.Zero) }
     //   val imagePainter = remember { BitmapPainter(yourBitmapImage) }
        val stateVertical = rememberScrollState(0)
        val stateHorizontal = rememberScrollState(0)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(13.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        offset.value += IntOffset(dragAmount.x.toInt(),dragAmount.y.toInt())
                        change.consumeAllChanges()
                    }
                }
                .verticalScroll(stateVertical)
                .horizontalScroll(stateHorizontal)
        ) {
            Image(
                painter = painterResource("test-image.png"),
                contentDescription = "Your Image",
                modifier = Modifier.offset { offset.value }
            )
        }
    }
}