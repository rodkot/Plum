package ru.nsu.ccfit.plum.component

import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor

@Composable
fun widthBox(widthBrunch: MutableState<Float>, range: ClosedFloatingPointRange<Float>, steps: Int) {
    Box(contentAlignment = Alignment.Center) {
        Column {
            Text(text = "Толщина: ${widthBrunch.value.toInt()}", color = Color.Black)
            Spacer(Modifier.height(10.dp))
            Slider(
                value = widthBrunch.value,
                modifier = Modifier.width(200.dp).height(10.dp),
                valueRange = range,
                steps = steps,
                onValueChange = {
                    widthBrunch.value = it
                }
            )
        }
    }
}

@Composable
fun rotationBox(rotation: MutableState<Float>, range: ClosedFloatingPointRange<Float>, steps: Int) {
    Box(contentAlignment = Alignment.Center) {
        Column {
            Text(text = "Поворот: ${rotation.value.toInt()} градусов", color = Color.Black)
            Spacer(Modifier.height(10.dp))
            Slider(
                value = rotation.value,
                modifier = Modifier.width(200.dp).height(10.dp),
                valueRange = range,
                steps = steps,
                onValueChange = {
                    rotation.value = it
                }
            )
        }
    }
}

@Composable
fun radiusBox(radius: MutableState<Float>, range: ClosedFloatingPointRange<Float>, steps: Int) {
    Box(contentAlignment = Alignment.Center) {
        Column {
            Text(text = "Радиус: ${radius.value.toInt()}", color = Color.Black)
            Spacer(Modifier.height(10.dp))
            Slider(
                value = radius.value,
                modifier = Modifier.width(200.dp).height(10.dp),
                valueRange = range,
                steps = steps,
                onValueChange = {
                    radius.value = it
                }
            )
        }
    }
}

@Composable
fun countVerticesBox(countVertices: MutableState<Float>, range: ClosedFloatingPointRange<Float>, steps: Int) {
    Box(contentAlignment = Alignment.Center) {
        Column {
            Text(text = "Количество вершин: ${countVertices.value.toInt()} ", color = Color.Black)
            Spacer(Modifier.height(10.dp))
            Slider(
                value = countVertices.value,
                modifier = Modifier.width(200.dp).height(10.dp),
                valueRange = range,
                steps = steps,
                onValueChange = {
                    countVertices.value = it
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