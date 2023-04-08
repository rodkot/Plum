package ru.nsu.ccfit.plum.button.impl

import ru.nsu.ccfit.plum.button.FilterSettingButton
import ru.nsu.ccfit.plum.dialog.impl.tools.GammaCorrectionDialog
import ru.nsu.ccfit.plum.icon.impl.ImageIcon
import ru.nsu.ccfit.plum.tool.filter.GammaCorrectionFilter

class GammaCorrectionButton(checked: Boolean, onClick: () -> Unit) :
    FilterSettingButton(
        GammaCorrectionFilter,
        GammaCorrectionDialog(),
        checked,
        ImageIcon("tool/gamma-logo.png"),
        onClick
    )