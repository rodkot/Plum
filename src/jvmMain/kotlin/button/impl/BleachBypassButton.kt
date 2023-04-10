package ru.nsu.ccfit.plum.button.impl

import ru.nsu.ccfit.plum.button.FilterButton
import ru.nsu.ccfit.plum.icon.impl.ImageIcon
import ru.nsu.ccfit.plum.tool.filter.BleachBypassFilter
import ru.nsu.ccfit.plum.tool.filter.GrayScaleFilter

class BleachBypassButton(checked: Boolean, onClick: () -> Unit) :
    FilterButton(BleachBypassFilter, checked, ImageIcon("tool/gray-scale.png"), onClick)