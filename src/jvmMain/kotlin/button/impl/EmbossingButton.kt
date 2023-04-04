package ru.nsu.ccfit.plum.button.impl

import compose.icons.FeatherIcons
import compose.icons.feathericons.Archive
import ru.nsu.ccfit.plum.button.FilterButton
import ru.nsu.ccfit.plum.icon.impl.ImageIcon
import ru.nsu.ccfit.plum.icon.impl.VectorIcon
import ru.nsu.ccfit.plum.tool.filter.EmbossingFilter
import ru.nsu.ccfit.plum.tool.filter.GrayScaleFilter

class EmbossingButton(checked: Boolean, onClick: () -> Unit) :
    FilterButton(EmbossingFilter, checked, ImageIcon("tool/embossing-logo.png"), onClick)