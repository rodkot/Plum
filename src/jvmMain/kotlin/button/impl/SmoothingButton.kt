package ru.nsu.ccfit.plum.button.impl

import compose.icons.FeatherIcons
import compose.icons.feathericons.Filter
import ru.nsu.ccfit.plum.button.FilterSettingButton
import ru.nsu.ccfit.plum.dialog.impl.tools.SmoothingFilterDialog
import ru.nsu.ccfit.plum.tool.filter.SmoothingFilter
import ru.nsu.ccfit.plum.icon.impl.VectorIcon

// TODO: По добавлению фильтра
// Здесь нужно добавить кнопку для отображения фильтра
class SmoothingButton(checked: Boolean, onClick: () -> Unit) :
    FilterSettingButton(
        SmoothingFilter
        /*TODO: По добавлению фильра
    Не забудь здесь указать новый фильр
    */, SmoothingFilterDialog(), checked, VectorIcon(FeatherIcons.Filter), onClick
    )