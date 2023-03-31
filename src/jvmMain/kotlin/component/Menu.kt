package ru.nsu.ccfit.plum.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuScope
import ru.nsu.ccfit.plum.filter.Filter
import ru.nsu.ccfit.plum.filter.GrayScaleFilter
import ru.nsu.ccfit.plum.filter.RotateFilter
import ru.nsu.ccfit.plum.filter.SmoothingFilter

class Menu() {

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun render(frameWindowScope: FrameWindowScope) {
        frameWindowScope.MenuBar {
            Menu("Файл", mnemonic = 'F') {
                Item(
                    "Сохранить",
                    onClick = { Controller.save.value = true },
                    shortcut = KeyShortcut(Key.S, ctrl = true)
                )
                Item(
                    "Открыть",
                    onClick = { Controller.open.value = true },
                    shortcut = KeyShortcut(Key.P, ctrl = true)
                )
            }
            Menu("Фильтры", mnemonic = 'I') {
                // TODO По добавлению фильтра
                // Здесь необходимо добавить схожую конструкцию
                createFilterMenuItem(GrayScaleFilter)
                createFilterMenuItem(SmoothingFilter)
                createFilterMenuItem(RotateFilter)
            }
            Menu("Справка", mnemonic = 'I') {
                Item("Инструкция", onClick = { Controller.instruction.value = true })
                Item("О программе", onClick = { Controller.about.value = true })
            }
        }
    }

    /**
     * Функция создания для фильра нового объекта меню
     * @param filter добавлямый фильтр
     */
    @Composable
    fun MenuScope.createFilterMenuItem(filter: Filter) =
        CheckboxItem(
            filter.name,
            checked = Controller.filter.value == filter,
            onCheckedChange = {
                Controller.filter.value = filter
            }
        )

    object Controller {
        var save = mutableStateOf(false)
        var open = mutableStateOf(false)
        var filter = mutableStateOf<Filter>(SmoothingFilter)
        var instruction = mutableStateOf(false)
        var about = mutableStateOf(false)
    }
}
