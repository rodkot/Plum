package ru.nsu.ccfit.plum.button.impl

import compose.icons.FeatherIcons
import compose.icons.feathericons.Filter
import ru.nsu.ccfit.plum.button.FilterButton
import ru.nsu.ccfit.plum.filter.GrayScaleFilter
import ru.nsu.ccfit.plum.icon.impl.VectorIcon

class GrayScaleButton(checked: Boolean, onClick: () -> Unit) :
    FilterButton(GrayScaleFilter, checked, VectorIcon(FeatherIcons.Filter), onClick)