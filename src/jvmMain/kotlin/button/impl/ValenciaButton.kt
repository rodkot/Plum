package ru.nsu.ccfit.plum.button.impl

import ru.nsu.ccfit.plum.button.FilterButton
import ru.nsu.ccfit.plum.icon.impl.ImageIcon
import ru.nsu.ccfit.plum.tool.filter.ValenciaFilter

class ValenciaButton(checked: Boolean, onClick: () -> Unit) :
        FilterButton(ValenciaFilter, checked, ImageIcon("drawable/tool/valencia.png"), onClick)