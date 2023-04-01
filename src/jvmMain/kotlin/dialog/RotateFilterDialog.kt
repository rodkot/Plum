package ru.nsu.ccfit.plum.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import ru.nsu.ccfit.plum.component.angleBox
import ru.nsu.ccfit.plum.component.countVerticesBox
import ru.nsu.ccfit.plum.filter.RotateFilter
import ru.nsu.ccfit.plum.filter.SmoothingFilter

class RotateFilterDialog: FilterDialog(SmoothingFilter) {
    private val angle = mutableStateOf(RotateFilter.angle)

    override fun updateFilter() {
        RotateFilter.angle = angle.value
    }

    @Composable
    override fun settingBox() {
        angleBox(angle, -180f..180f, 10)
    }
}