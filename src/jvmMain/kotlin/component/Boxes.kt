package ru.nsu.ccfit.plum.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor

/**
 * Слайдер для выбора натурального значения
 * @param name название параметра
 * @param valueInt изменяемое значение
 * @param range пределы выбора величины
 */
@Composable
fun rangeBox(name: String, valueInt: MutableState<Int>, range: IntRange) {
    Box(contentAlignment = Alignment.Center) {
        var value by remember { mutableStateOf(valueInt.value) }
        Column {
            Text(text = "${name}: ${valueInt.value}", color = Color.Black)

            Spacer(Modifier.height(10.dp))
            Row(Modifier.width(400.dp).height(50.dp)) {
                Column(Modifier.weight(2f)) {
                    Slider(
                        value = value.toFloat(),
                        valueRange = range.first.toFloat()..range.last.toFloat(),
                        steps = range.last - range.first,
                        modifier = Modifier.align(Alignment.Start),
                        onValueChange = { newValue ->
                            valueInt.value = newValue.toInt()
                            value = newValue.toInt()
                        }
                    )
                }

                var error by remember { mutableStateOf(false) }

                TextField(
                    value = value.toString(),
                    isError = error,
                    onValueChange = { newValue ->
                        val newIntValue = newValue.toIntOrNull()
                        if (newIntValue != null) {
                            value = newIntValue
                            error = !range.contains(newIntValue)
                            if (!error) {
                                valueInt.value = newIntValue
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    )
                )
            }

        }

    }
}


/**
 * Слайдер для выбора вещественного значения
 * @param name название параметра
 * @param valueFloat изменяемое значение
 * @param range пределы выбора величины
 * @param steps количество шагов для выбора значения
 */
@Composable
fun rangeBox(name: String, valueFloat: MutableState<Float>, range: ClosedFloatingPointRange<Float>, steps: Int) {
    Box(contentAlignment = Alignment.Center) {
        var value by remember { mutableStateOf(valueFloat.value) }
        Column() {
            Text(text = "${name}: ${valueFloat.value}", color = Color.Black)

            Spacer(Modifier.height(10.dp))
            Row(Modifier.width(400.dp).height(50.dp)) {
                Column(Modifier.weight(2f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Slider(
                        value = value,
                        valueRange = range,
                        steps = steps,
                        modifier = Modifier.align(Alignment.Start),
                        onValueChange = { newValue ->
                            valueFloat.value = newValue
                            value = newValue
                        }
                    )
                }

                var error by remember { mutableStateOf(false) }

                TextField(
                    value = value.toString(),
                    isError = error,
                    onValueChange = { newValue ->
                        val newIntValue = newValue.toFloatOrNull()
                        if (newIntValue != null) {
                            value = newIntValue
                            error = !range.contains(newIntValue)
                            if (!error) {
                                valueFloat.value = newIntValue
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    )
                )
            }

        }

    }
}


/**
 * Селектор ползволяющий выбрать элемент списка через checkbox
 * @param name название параметра
 * @param size изменяемое значение
 * @param values список значений
 * @param T тип параметра реализующий интерфейс Param
 * @see Param
 */
@Composable
fun <T : Param> selectItem(name: String, size: MutableState<T>, values: Array<T>) {
    val stateHorizontal = rememberScrollState(0)
    Column {
        Text(
            text = "${name}: ${size.value}", color = Color.Black
        )
        Row(
            modifier = Modifier
                .horizontalScroll(stateHorizontal)
        ) {
            values.forEach { v ->
                checkBox(v.getName(), v, size) {
                    size.value = it
                }
            }
        }
    }

}

/**
 * Чекбокс
 * @param name название
 * @param value значение
 * @param current текущее значение
 * @param onCheckedChange метод вызывающийся при выборе
 * @param T тип параметра
 */
@Composable
fun <T> checkBox(name: String, value: T, current: MutableState<T>, onCheckedChange: (T) -> Unit) {
    Box(modifier = Modifier.padding(10.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RadioButton(
                selected = current.value == value,
                onClick = { onCheckedChange(value) }
            )
            Text(
                text = name,
            )
        }
    }
}


@Composable
fun angleBox(angle: MutableState<Int>, range: ClosedFloatingPointRange<Float>, steps: Int) {
    Box(contentAlignment = Alignment.Center) {
        Column {
            Text(text = "Угол поворота: ${angle.value} ", color = Color.Black)
            Spacer(Modifier.height(10.dp))
            Slider(
                value = angle.value.toFloat(),
                modifier = Modifier.width(200.dp).height(10.dp),
                valueRange = range,
                steps = steps,
                onValueChange = {
                    angle.value = it.toInt()
                }
            )
        }
    }
}

@Composable
fun circleColorSelectionButton(color: Color, onClick: (Color) -> Unit) {
    Spacer(
        modifier = Modifier.background(
            color,
            shape = CircleShape
        )
            .height(20.dp)
            .width(20.dp)
            .clickable { onClick.invoke(color) }
    )
}

@Composable
fun colorStandardSelectionBox(currentColor: MutableState<HsvColor>) {
    Row {

        circleColorSelectionButton(Color.Red) {
            currentColor.value = HsvColor.from(it)
        }
        Spacer(Modifier.fillMaxWidth().weight(1f))
        circleColorSelectionButton(Color.Yellow) {
            currentColor.value = HsvColor.from(it)
        }
        Spacer(Modifier.fillMaxWidth().weight(1f))
        circleColorSelectionButton(Color.Green) {
            currentColor.value = HsvColor.from(it)
        }
        Spacer(Modifier.fillMaxWidth().weight(1f))
        circleColorSelectionButton(Color.Cyan) {
            currentColor.value = HsvColor.from(it)
        }
        Spacer(Modifier.fillMaxWidth().weight(1f))
        circleColorSelectionButton(Color.Blue) {
            currentColor.value = HsvColor.from(it)
        }
        Spacer(Modifier.fillMaxWidth().weight(1f))
        circleColorSelectionButton(Color.Magenta) {
            currentColor.value = HsvColor.from(it)
        }
    }
}


@Composable
fun colorSelectionBox(currentColor: MutableState<HsvColor>) {
    Box() {
        val color = remember { currentColor }
        Column {
            Text(text = "Цвет:", color = Color.Black)
            Spacer(
                modifier = Modifier.background(
                    color.value.toColor(),
                    shape = RectangleShape
                ).fillMaxWidth()
                    .height(20.dp)
            )
            Spacer(Modifier.fillMaxWidth().padding(10.dp))
            Box() {
                ClassicColorPicker(
                    color = color.value,
                    modifier = Modifier.fillMaxWidth().height(150.dp),
                    showAlphaBar = false,
                    onColorChanged = { hsvColor: HsvColor ->
                        color.value = hsvColor
                    })
            }
            Spacer(Modifier.fillMaxWidth().padding(10.dp))
            colorStandardSelectionBox(currentColor)
        }
    }
}

fun defaultScrollbarStyle() = ScrollbarStyle(
    minimalHeight = 16.dp,
    thickness = 10.dp,
    shape = RoundedCornerShape(5.dp),
    hoverDurationMillis = 300,
    unhoverColor = Color.Black,
    hoverColor = Color.Black.copy(alpha = 0.7f)
)