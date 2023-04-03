package ru.nsu.ccfit.plum.dialog.impl.tools

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import ru.nsu.ccfit.plum.component.selectItem
import ru.nsu.ccfit.plum.dialog.ToolDialog
import ru.nsu.ccfit.plum.tool.InterpolationTool

class InterpolationToolDialog: ToolDialog(InterpolationTool) {
    // TODO По добавлению фильтра
    // Изменяемые параметры
    private val interpolation = mutableStateOf(InterpolationTool.interpolation)

    // TODO По добавлению фильтра
    // Здесь нужно приминить настройки выбранные пользователем
    override fun updateFilter() {
        InterpolationTool.interpolation = interpolation.value
    }

    // TODO По добавлению фильтра
    // В функцию нужно добавить необходимые инструменты для изменения параметров
    @Composable
    override fun settingBox() {
        selectItem("Режим", interpolation, InterpolationTool.Interpolation.values())

    }
}