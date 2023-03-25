package ru.nsu.ccfit.plum.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import ru.nsu.ccfit.plum.filter.Filter


class ToolBar(
    private val currentTool: MutableState<Filter>
) : Renderable {

    @Composable
    override fun render() {
        TopAppBar(title = {
            Row {
                Text(currentTool.value.getNameTool())
            }
        }, modifier = Modifier.fillMaxWidth(),
            actions = {
                Row {

                }
            })
    }
}