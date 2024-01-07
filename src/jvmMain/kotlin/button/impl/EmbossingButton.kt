package ru.nsu.ccfit.plum.button.impl


import ru.nsu.ccfit.plum.button.FilterButton
import ru.nsu.ccfit.plum.icon.impl.ImageIcon
import ru.nsu.ccfit.plum.tool.filter.EmbossingFilter

class EmbossingButton(checked: Boolean, onClick: () -> Unit) :
    FilterButton(EmbossingFilter, checked, ImageIcon("drawable/tool/embossing-logo.png"), onClick)