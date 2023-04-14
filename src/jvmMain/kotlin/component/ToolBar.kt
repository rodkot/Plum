package ru.nsu.ccfit.plum.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.nsu.ccfit.plum.button.impl.*
import ru.nsu.ccfit.plum.tool.filter.*
import ru.nsu.ccfit.plum.button.impl.GrayScaleButton
import ru.nsu.ccfit.plum.button.impl.SmoothingButton

class ToolBar(
    private val currentFilter: MutableState<Filter>,
    private val interpolation: MutableState<Boolean>
) : Renderable {

    @Composable
    override fun render() {
        TopAppBar(title = {

        }, modifier = Modifier.fillMaxWidth(),
            actions = {
                Row(Modifier.weight(5f)) {
                    Column(Modifier.fillMaxWidth()) {
                        Row(Modifier.align(Alignment.CenterHorizontally)) {
                            // TODO По добавлению фильтра
                            // Добавить схожую конструкцию
                            SmoothingButton(currentFilter.value is SmoothingFilter) {
                                currentFilter.value = SmoothingFilter
                            }.render()

                            EmbossingButton(currentFilter.value is EmbossingFilter) {
                                currentFilter.value = EmbossingFilter
                            }.render()

                            BorderHighlightingButton(currentFilter.value is BorderHighlightingFilter) {
                                currentFilter.value = BorderHighlightingFilter
                            }.render()

                            GrayScaleButton(currentFilter.value is GrayScaleFilter) {
                                currentFilter.value = GrayScaleFilter
                            }.render()
                            DitheringButton(currentFilter.value is DitheringFilter) {
                                currentFilter.value = DitheringFilter
                            }.render()

                            RotateButton(currentFilter.value is RotateFilter) {
                                currentFilter.value = RotateFilter
                            }.render()

                            MirrorButton(currentFilter.value is MirrorFilter) {
                                currentFilter.value = MirrorFilter
                            }.render()

                            InversionButton(currentFilter.value is InversionFilter) {
                                currentFilter.value = InversionFilter
                            }.render()
                        }
                    }
                }

                Row(Modifier.weight(1f)) {
                    Column(Modifier.fillMaxWidth()) {
                        Row(Modifier.align(Alignment.End)) {
                            InterpolationButton(interpolation.value) {
                                interpolation.value = !interpolation.value
                            }.render()
                        }
                    }

                }
            })
    }
}