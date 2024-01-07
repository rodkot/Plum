package ru.nsu.ccfit.plum.button.impl

import ru.nsu.ccfit.plum.button.FilterSettingButton
import ru.nsu.ccfit.plum.dialog.impl.tools.GammaFilterDialog
import ru.nsu.ccfit.plum.icon.impl.ImageIcon
import ru.nsu.ccfit.plum.tool.filter.GammaFilter

class GammaButton(checked: Boolean, onClick: () -> Unit) :
    FilterSettingButton(GammaFilter, GammaFilterDialog(), checked, ImageIcon("drawable/tool/gamma.png"), onClick)