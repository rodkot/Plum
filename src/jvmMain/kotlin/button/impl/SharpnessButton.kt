package ru.nsu.ccfit.plum.button.impl

import ru.nsu.ccfit.plum.button.FilterButton
import ru.nsu.ccfit.plum.icon.impl.ImageIcon
import ru.nsu.ccfit.plum.tool.filter.SharpnessFilter

class SharpnessButton(checked: Boolean, onClick: () -> Unit): FilterButton(
    SharpnessFilter,
    checked,
    ImageIcon("drawable/tool/sharpness.png"),
    onClick
)