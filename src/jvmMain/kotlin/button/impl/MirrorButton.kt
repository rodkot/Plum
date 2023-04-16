package ru.nsu.ccfit.plum.button.impl

import ru.nsu.ccfit.plum.button.FilterSettingButton
import ru.nsu.ccfit.plum.dialog.impl.tools.MirrorFilterDialog
import ru.nsu.ccfit.plum.icon.impl.ImageIcon
import ru.nsu.ccfit.plum.tool.filter.MirrorFilter

class MirrorButton(checked: Boolean, onClick: () -> Unit) :
    FilterSettingButton(MirrorFilter, MirrorFilterDialog(), checked, ImageIcon("tool/mirror.png"), onClick)