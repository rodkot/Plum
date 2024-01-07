package ru.nsu.ccfit.plum.button.impl

import ru.nsu.ccfit.plum.button.FilterButton
import ru.nsu.ccfit.plum.icon.impl.ImageIcon
import ru.nsu.ccfit.plum.tool.filter.AquarellFilter

class AquarellButton(checked: Boolean, onClick: () -> Unit) :
    FilterButton(AquarellFilter, checked, ImageIcon("drawable/tool/aquarelle.png"), onClick)