package ru.nsu.ccfit.plum.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.nsu.ccfit.plum.tool.Tool

/**
 * Конструктор диалогового окна для инструментов
 * @param tool инструмент, для которого отображается окно
 */
abstract class ToolDialog(
    private val tool: Tool
) : Dialog {

    /**
     * Функция вызывющийся при решении пользователя приминить настройки
     */
    abstract fun updateFilter()

    /**
     * Функция отображающая инструменты для изменения параметров
     */
    @Composable
    abstract fun settingBox()

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun openDialog(confirmClick: () -> Unit, cancelClick: () -> Unit) {
        AlertDialog(
            onDismissRequest = {
                // cancelClick.invoke()
            },
            title = { Text(text = "Настройка ${tool.name}") },
            modifier = Modifier.widthIn(Dp.Unspecified,500.dp),
            text = {
                Column {
                    settingBox()
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.weight(1f).padding(8.dp),
                        onClick = {

                            updateFilter()

                            confirmClick()
                            cancelClick()
                        }
                    ) {
                        Text("Применить")
                    }
                    Button(
                        modifier = Modifier.weight(1f).padding(8.dp),
                        onClick = {
                            cancelClick()
                        }
                    ) {
                        Text("Отмена")
                    }
                }
            }
        )
    }

}