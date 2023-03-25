package ru.nsu.ccfit.plum.dialog

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ManualDialog(cancelClick: () -> Unit) {

    AlertDialog(
        onDismissRequest = {
            // cancelClick.invoke()
        },
        title = { Text(text = "Инструкция Kivis") },
        text = {
            Column {
                Text(text = "1.Выбирите инструмент рисования в панеле инструментов или в меню;")
                Text(text = "2.При нажатии правой кнопке по инструменту, откроется диалоговое окно для настройки инструмента, после подтвердите кнопкой\"Принять\";")
                Text(text = "3.Нажмите мышью в место применения инструмента;")
                Spacer(Modifier.width(20.dp))
            }
        },
        modifier = Modifier.width(500.dp),
        buttons = {
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    cancelClick.invoke()
                }
            ) {
                Text("Отмена")
            }
        }
    )
}