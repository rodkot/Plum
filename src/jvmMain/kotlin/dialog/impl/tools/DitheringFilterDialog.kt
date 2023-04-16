package ru.nsu.ccfit.plum.dialog.impl.tools

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import ru.nsu.ccfit.plum.component.rangeBox
import ru.nsu.ccfit.plum.component.selectItem
import ru.nsu.ccfit.plum.dialog.ToolDialog
import ru.nsu.ccfit.plum.tool.filter.DitheringFilter

class DitheringFilterDialog: ToolDialog(DitheringFilter) {

        private val quantizationRed = mutableStateOf(DitheringFilter.redQuantizationNum)
        private val quantizationGreen = mutableStateOf(DitheringFilter.greenQuantizationNum)
        private val quantizationBlue = mutableStateOf(DitheringFilter.blueQuantizationNum)
        private val algorithm = mutableStateOf(DitheringFilter.algorithm)

        override fun updateFilter() {
            DitheringFilter.redQuantizationNum = quantizationRed.value
            DitheringFilter.greenQuantizationNum = quantizationGreen.value
            DitheringFilter.blueQuantizationNum = quantizationBlue.value
            DitheringFilter.algorithm = algorithm.value
        }

        @Composable
        override fun settingBox() {
            rangeBox("Красный", quantizationRed,2..128)
            rangeBox("Зеленый", quantizationGreen,2..128)
            rangeBox("Синий", quantizationBlue,2..128)
            selectItem("Алгоритм", algorithm, DitheringFilter.Algorithm.values())
        }
}