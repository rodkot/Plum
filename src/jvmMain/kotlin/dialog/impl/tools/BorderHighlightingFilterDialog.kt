package ru.nsu.ccfit.plum.dialog.impl.tools

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import ru.nsu.ccfit.plum.component.rangeBox
import ru.nsu.ccfit.plum.component.selectItem
import ru.nsu.ccfit.plum.dialog.ToolDialog
import ru.nsu.ccfit.plum.tool.filter.BorderHighlightingFilter

class BorderHighlightingFilterDialog : ToolDialog(BorderHighlightingFilter) {
    private val algorithm = mutableStateOf(BorderHighlightingFilter.algorithm)
    private val binarization = mutableStateOf(BorderHighlightingFilter.binarization)

    override fun updateFilter() {
        BorderHighlightingFilter.algorithm = algorithm.value
        BorderHighlightingFilter.binarization = binarization.value
    }

    @Composable
    override fun settingBox() {
        selectItem("Алгоритм", algorithm, BorderHighlightingFilter.Algorithm.values())
        rangeBox("Порог бинаризации",binarization,2..254)
    }
}