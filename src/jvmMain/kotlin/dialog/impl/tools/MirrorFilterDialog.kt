package ru.nsu.ccfit.plum.dialog.impl.tools

import androidx.compose.foundation.layout.*
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.nsu.ccfit.plum.dialog.ToolDialog
import ru.nsu.ccfit.plum.tool.enums.Alignment as EnumAlignment
import ru.nsu.ccfit.plum.tool.filter.MirrorFilter

class MirrorFilterDialog : ToolDialog(MirrorFilter) {
    private var alignment = mutableStateOf(MirrorFilter.alignment)

    override fun updateFilter() {
        //TODO - разобраться, почему не срабатывает
        MirrorFilter.alignment
    }

    @Composable
    override fun settingBox() {
        Box(contentAlignment = androidx.compose.ui.Alignment.Center) {
            Column {
                Row(Modifier.width(400.dp).height(50.dp)) {
                    RadioButton(
                        selected = alignment.value == EnumAlignment.VERTICAL,
                        onClick = {
                            alignment.value = EnumAlignment.VERTICAL
                            MirrorFilter.alignment = EnumAlignment.VERTICAL
                        }
                    )
                    Text(text = "Вертикальное")
                }
                Row(Modifier.width(400.dp).height(50.dp)) {
                    RadioButton(
                        selected = alignment.value == EnumAlignment.HORIZONTAL,
                        onClick = {
                            alignment.value = EnumAlignment.HORIZONTAL
                            MirrorFilter.alignment = EnumAlignment.HORIZONTAL
                        }
                    )
                    Text(text = "Горизонтальное")
                }
            }
        }
    }
}