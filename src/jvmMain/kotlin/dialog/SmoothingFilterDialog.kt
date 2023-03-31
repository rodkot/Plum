package ru.nsu.ccfit.plum.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import ru.nsu.ccfit.plum.component.countVerticesBox
import ru.nsu.ccfit.plum.filter.SmoothingFilter

// TODO По добавлению фильтра
// При необходимости изменения параметров необходимо добавить диалоговое окно
class SmoothingFilterDialog : FilterDialog(SmoothingFilter) {
    // TODO По добавлению фильтра
    // Изменяемые параметры
    private val sigma = mutableStateOf(SmoothingFilter.sigma)

    // TODO По добавлению фильтра
    // Здесь нужно приминить настройки выбранные пользователем
    override fun updateFilter() {
        SmoothingFilter.sigma = sigma.value
    }

    // TODO По добавлению фильтра
    // В функцию нужно добавить необходимые инструменты для изменения параметров
    @Composable
    override fun settingBox() {
        countVerticesBox(sigma, 3f..13f, 10)
    }
}