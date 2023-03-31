package ru.nsu.ccfit.plum.button

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import ru.nsu.ccfit.plum.dialog.Dialog
import ru.nsu.ccfit.plum.filter.Filter
import ru.nsu.ccfit.plum.icon.Icon

/**
 * Конструктор кнопки для фильтра c настрокой параметров
 * @param filter фильр для которого настраивается кнопка
 * @param checked boolean указывающий выбрана ли кнопка
 * @param dialog диалоговое окно, используемое для настройки фильра
 * @param icon иконка для кнопки
 * @param onClick функция вызывающийся при нажатии кнопки
 * @see Filter
 */
abstract class FilterSettingButton(
    filter: Filter,
    private val dialog: Dialog,
    checked: Boolean,
    icon: Icon,
    private val onClick: () -> Unit
) :
    ToolButton(filter, checked, icon) {
    private val viewDialog = mutableStateOf(false)

    final override fun rightClick() {
        viewDialog.value = true
    }

    final override fun leftClick() {
       onClick.invoke()
    }

    private fun closeSetting() {
        leftClick()
        viewDialog.value = false
    }

    @Composable
    override fun render() {
        val dialogOpen = remember { viewDialog }
        super.render()
        if (dialogOpen.value) {
            dialog.openDialog({ closeSetting() }, { dialogOpen.value = false })
        }
    }
}