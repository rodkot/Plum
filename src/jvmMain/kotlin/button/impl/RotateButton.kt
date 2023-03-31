package ru.nsu.ccfit.plum.button.impl

import compose.icons.FeatherIcons
import compose.icons.feathericons.RotateCw
import ru.nsu.ccfit.plum.button.FilterSettingButton
import ru.nsu.ccfit.plum.dialog.RotateFilterDialog
import ru.nsu.ccfit.plum.filter.RotateFilter
import ru.nsu.ccfit.plum.icon.impl.VectorIcon

class RotateButton(checked: Boolean, onClick: () -> Unit) :
    FilterSettingButton(RotateFilter, RotateFilterDialog(), checked, VectorIcon(FeatherIcons.RotateCw), onClick)