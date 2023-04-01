package ru.nsu.ccfit.plum

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ru.nsu.ccfit.plum.component.*
import ru.nsu.ccfit.plum.filter.Filter
import ru.nsu.ccfit.plum.filter.SmoothingFilter
import javax.imageio.ImageIO

class MainWindowController {
    companion object {
        val currentFilter = mutableStateOf<Filter>(SmoothingFilter)

        var size = mutableStateOf(IntSize.Zero)
        var image = ImageIO.read(Thread.currentThread().contextClassLoader.getResource("test-image.png")).toPlumImage()

        val toolBar = ToolBar(currentFilter)
        val canvas = PaintCanvas()
    }
}


@OptIn(ExperimentalFoundationApi::class)
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
        val maxOffset = remember { mutableStateOf(IntOffset.Zero) }
        val stateVertical = rememberScrollState(0)
        val stateHorizontal = rememberScrollState(0)

        val imageWidth = MainWindowController.image.width.dp
        val imageHeight = MainWindowController.image.height.dp

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(13.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        offset.value += IntOffset(dragAmount.x.toInt(), dragAmount.y.toInt())

                        // Calculate the maximum offset that restricts dragging the image beyond the right and bottom edge
                        val maxWidth = (imageWidth - size.width.dp).coerceAtLeast(0.dp).toPx()
                        val maxHeight = (imageHeight - size.height.toDp()).coerceAtLeast(0.dp).toPx()
                        maxOffset.value = IntOffset(maxWidth.toInt(), maxHeight.toInt())

                        // Apply the offset constraints
                        offset.value = IntOffset(
                            x = offset.value.x.coerceIn(0, maxOffset.value.x),
                            y = offset.value.y.coerceIn(0, maxOffset.value.y)
                        )

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