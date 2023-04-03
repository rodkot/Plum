package ru.nsu.ccfit.plum.button.impl

import compose.icons.FeatherIcons
import compose.icons.feathericons.Filter
import ru.nsu.ccfit.plum.button.FilterSettingButton
import ru.nsu.ccfit.plum.dialog.impl.tools.SmoothingFilterDialog
import ru.nsu.ccfit.plum.tool.filter.SmoothingFilter
import ru.nsu.ccfit.plum.icon.impl.VectorIcon

// TODO По добавлению фильтра
// Здесь нужно добавить кнопку для отображения фильтра
class SmoothingButton(checked: Boolean, onClick: () -> Unit) :
    FilterSettingButton(SmoothingFilter, SmoothingFilterDialog(), checked, VectorIcon(FeatherIcons.Filter), onClick)