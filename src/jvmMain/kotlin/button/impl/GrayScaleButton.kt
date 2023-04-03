package ru.nsu.ccfit.plum.button.impl

import ru.nsu.ccfit.plum.button.FilterButton
import ru.nsu.ccfit.plum.tool.filter.GrayScaleFilter
import ru.nsu.ccfit.plum.icon.impl.ImageIcon

class GrayScaleButton(checked: Boolean, onClick: () -> Unit) :
    FilterButton(GrayScaleFilter, checked, ImageIcon("tool/gray-scale.png"), onClick)