package ru.nsu.ccfit.plum.dialog.impl.tools

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import ru.nsu.ccfit.plum.component.rangeBox
import ru.nsu.ccfit.plum.component.selectItem
import ru.nsu.ccfit.plum.dialog.ToolDialog
import ru.nsu.ccfit.plum.tool.filter.DitheringFilter
import ru.nsu.ccfit.plum.tool.filter.SmoothingFilter

class DitheringFilterDialog : ToolDialog(DitheringFilter) {

    private val quantizationRed = mutableStateOf(DitheringFilter.quantizationRed)
    private val quantizationGreen = mutableStateOf(DitheringFilter.quantizationGreen)
    private val quantizationBlue = mutableStateOf(DitheringFilter.quantizationBlue)

    private val dithering = mutableStateOf(DitheringFilter.dithering)


    override fun updateFilter() {
        DitheringFilter.quantizationRed = quantizationRed.value
        DitheringFilter.quantizationBlue = quantizationBlue.value
        DitheringFilter.quantizationGreen = quantizationGreen.value

        DitheringFilter.dithering = dithering.value
    }

    @Composable
    override fun settingBox() {
        selectItem("Алгоритм", dithering, DitheringFilter.Dithering.values())

        rangeBox("Красный", quantizationRed, 2..128)
        rangeBox("Зеленый", quantizationGreen, 2..128)
        rangeBox("Синий", quantizationBlue, 2..128)
    }
}