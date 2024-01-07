package ru.nsu.ccfit.plum.button.impl

import ru.nsu.ccfit.plum.button.FilterSettingButton
import ru.nsu.ccfit.plum.dialog.impl.tools.BorderHighlightingFilterDialog
import ru.nsu.ccfit.plum.icon.impl.ImageIcon
import ru.nsu.ccfit.plum.tool.filter.BorderHighlightingFilter

class BorderHighlightingButton(checked: Boolean, onClick: () -> Unit) : FilterSettingButton(
    BorderHighlightingFilter, BorderHighlightingFilterDialog(), checked, ImageIcon("drawable/tool/border_highlighting_logo.png"), onClick
)