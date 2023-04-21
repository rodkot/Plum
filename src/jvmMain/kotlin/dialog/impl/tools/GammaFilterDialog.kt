package ru.nsu.ccfit.plum.dialog.impl.tools

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import ru.nsu.ccfit.plum.component.rangeBox
import ru.nsu.ccfit.plum.dialog.ToolDialog
import ru.nsu.ccfit.plum.tool.filter.GammaFilter

class GammaFilterDialog : ToolDialog(GammaFilter) {
    private val gamma = mutableStateOf(GammaFilter.gamma.toFloat())

    override fun updateFilter() {
        GammaFilter.gamma = gamma.value.toDouble()
    }

    @Composable
    override fun settingBox() {
        rangeBox("Гамма", gamma, 0.1f..10f, 50)
    }
}
