package ru.nsu.ccfit.plum.tool.filter

import ru.nsu.ccfit.plum.component.PlumImage

object GammaCorrectionFilter : Filter("Гамма коррекция") {
    var gamma = 0.5f

    override fun permit(image: PlumImage): PlumImage {
        TODO("Написать реализацию гамма коррекции")
    }
}