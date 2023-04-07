package ru.nsu.ccfit.plum.button.impl

import ru.nsu.ccfit.plum.button.FilterSettingButton
import ru.nsu.ccfit.plum.dialog.impl.tools.DitheringFilterDialog
import ru.nsu.ccfit.plum.icon.impl.ImageIcon
import ru.nsu.ccfit.plum.tool.filter.DitheringFilter

class DitheringButton (checked: Boolean, onClick: () -> Unit) : FilterSettingButton(
    DitheringFilter, DitheringFilterDialog(), checked, ImageIcon("tool/dithering_logo.png"), onClick
)