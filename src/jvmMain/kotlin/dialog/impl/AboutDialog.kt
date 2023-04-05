package ru.nsu.ccfit.plum.dialog.impl

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun AboutDialog(cancelClick: () -> Unit) {

    AlertDialog(
        onDismissRequest = {
            // cancelClick.invoke()
        },
        title = { Text(text = "О программе Kivis") },
        modifier = Modifier.width(300.dp),
        text = {
            Column {
                Text(text = "Разработчик:")
                Text(text = "Котов Родион", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Text(text = "студент 3 курса ФИТ НГУ", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.width(20.dp))
                Text(text = "Программа созадана в рамках курса \"Компьютерная и инженерная графика\" ", textAlign = TextAlign.Center)
                Spacer(Modifier.width(20.dp))
                Text(text = "г. Новосибириск", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.width(20.dp))
                Text(text = "2023 год", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }
        },
        buttons = {
            Button(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                onClick = {
                    cancelClick.invoke()
                }
            ) {
                Text("Отмена")
            }
        }

    )
}