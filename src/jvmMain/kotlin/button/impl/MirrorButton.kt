package ru.nsu.ccfit.plum.button.impl

import ru.nsu.ccfit.plum.button.FilterButton
import ru.nsu.ccfit.plum.icon.impl.ImageIcon
import ru.nsu.ccfit.plum.tool.filter.MirrorFilter

class MirrorButton(checked: Boolean, onClick: () -> Unit) :
    FilterButton(MirrorFilter, checked, ImageIcon("tool/mirror.png"), onClick)