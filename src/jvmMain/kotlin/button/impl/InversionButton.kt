package ru.nsu.ccfit.plum.button.impl

import ru.nsu.ccfit.plum.button.FilterButton
import ru.nsu.ccfit.plum.icon.impl.ImageIcon
import ru.nsu.ccfit.plum.tool.filter.InversionFilter

class InversionButton(checked: Boolean, onClick: () -> Unit) :
    FilterButton(InversionFilter, checked, ImageIcon("tool/inversion.png"), onClick)