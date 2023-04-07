package ru.nsu.ccfit.plum.dialog.impl.tools

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import ru.nsu.ccfit.plum.component.selectItem
import ru.nsu.ccfit.plum.dialog.ToolDialog
import ru.nsu.ccfit.plum.tool.filter.MirrorFilter

class MirrorFilterDialog : ToolDialog(MirrorFilter) {
    private val alignment = mutableStateOf(MirrorFilter.alignment)

    override fun updateFilter() {
        MirrorFilter.alignment = alignment.value
    }

    @Composable
    override fun settingBox() {
        selectItem("Вид отражения", alignment, MirrorFilter.Alignment.values())
    }
}