package ru.nsu.ccfit.plum.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.nsu.ccfit.plum.button.impl.GrayScaleButton
import ru.nsu.ccfit.plum.button.impl.SmoothingButton
import ru.nsu.ccfit.plum.filter.Filter
import ru.nsu.ccfit.plum.filter.GrayScaleFilter
import ru.nsu.ccfit.plum.filter.SmoothingFilter


class ToolBar(
    private val currentTool: MutableState<Filter>
) : Renderable {

    @Composable
    override fun render() {
        TopAppBar(title = {
//            Row {
//                Text(currentTool.value.name)
//            }
        }, modifier = Modifier.fillMaxWidth(),
            actions = {
                Column(Modifier.fillMaxWidth()){
                    Row(Modifier.align(Alignment.CenterHorizontally)) {
                        // TODO По добавлению фильтра
                        // Добавить схожую конструкцию
                        SmoothingButton(currentTool.value is SmoothingFilter) { currentTool.value = SmoothingFilter }.render()
                        GrayScaleButton(currentTool.value is GrayScaleFilter) { currentTool.value = GrayScaleFilter }.render()
                    }
                }

            })
    }
}