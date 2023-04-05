package ru.nsu.ccfit.plum.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import ru.nsu.ccfit.plum.component.rangeBox
import ru.nsu.ccfit.plum.tool.filter.RotateFilter

class RotateFilterDialog: ToolDialog(RotateFilter) {
    private val angle = mutableStateOf(RotateFilter.angle)

    override fun updateFilter() {
        RotateFilter.angle = angle.value
    }

    @Composable
    override fun settingBox() {
        rangeBox("Угол",angle,-180..180)
    }
}