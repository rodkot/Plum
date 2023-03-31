package ru.nsu.ccfit.plum.button

import ru.nsu.ccfit.plum.filter.Filter
import ru.nsu.ccfit.plum.icon.Icon

/**
 * Конструктор кнопки для фильтра без настроки параметров
 * @param filter фильр для которого настраивается кнопка
 * @param checked boolean указывающий выбрана ли кнопка
 * @param onClick функция вызыывющийся при нажатии кнопки
 * @param icon иконка для кнопки
 * @see Filter
 */
abstract class FilterButton(filter: Filter, checked: Boolean, icon: Icon, private val onClick: () -> Unit) : ToolButton(filter, checked, icon) {
    final override fun rightClick() {}
    final override fun leftClick() {
        onClick.invoke()
    }
}