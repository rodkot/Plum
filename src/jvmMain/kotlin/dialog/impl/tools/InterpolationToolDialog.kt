package ru.nsu.ccfit.plum.dialog.impl.tools

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import ru.nsu.ccfit.plum.component.selectItem
import ru.nsu.ccfit.plum.dialog.ToolDialog
import ru.nsu.ccfit.plum.tool.InterpolationTool

class InterpolationToolDialog: ToolDialog(InterpolationTool) {
    private val interpolation = mutableStateOf(InterpolationTool.interpolation)

    override fun updateFilter() {
        InterpolationTool.interpolation = interpolation.value
    }

    @Composable
    override fun settingBox() {
        selectItem("Режим", interpolation, InterpolationTool.Interpolation.values())

    }
}