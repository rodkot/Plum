package ru.nsu.ccfit.plum.dialog.impl.tools

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import ru.nsu.ccfit.plum.component.rangeBox
import ru.nsu.ccfit.plum.dialog.ToolDialog
import ru.nsu.ccfit.plum.tool.filter.GammaCorrectionFilter

class GammaCorrectionDialog : ToolDialog(GammaCorrectionFilter) {
    private val gamma = mutableStateOf(GammaCorrectionFilter.gamma)

    override fun updateFilter() {
        GammaCorrectionFilter.gamma = gamma.value
    }

    @Composable
    override fun settingBox() {
        rangeBox("Гамма параметр",gamma,0f..1f,10)
    }
}