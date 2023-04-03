package ru.nsu.ccfit.plum.dialog.impl.tools

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import ru.nsu.ccfit.plum.component.rangeBox
import ru.nsu.ccfit.plum.component.selectItem
import ru.nsu.ccfit.plum.dialog.ToolDialog
import ru.nsu.ccfit.plum.tool.filter.SmoothingFilter

// TODO По добавлению фильтра
// При необходимости изменения параметров необходимо добавить диалоговое окно
class SmoothingFilterDialog : ToolDialog(SmoothingFilter) {
    // TODO По добавлению фильтра
    // Изменяемые параметры
    private val sigma = mutableStateOf(SmoothingFilter.sigma)
    private val size = mutableStateOf(SmoothingFilter.size)

    // TODO По добавлению фильтра
    // Здесь нужно приминить настройки выбранные пользователем
    override fun updateFilter() {
        SmoothingFilter.sigma = sigma.value
    }

    // TODO По добавлению фильтра
    // В функцию нужно добавить необходимые инструменты для изменения параметров
    @Composable
    override fun settingBox() {

        rangeBox("Sigma", sigma, 0f..1f, 11)
        selectItem("Размер", size, SmoothingFilter.Size.values())

    }
}