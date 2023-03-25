package ru.nsu.ccfit.plum.button

import ru.nsu.ccfit.plum.component.Renderable

abstract class Button protected constructor() : Renderable {
    abstract fun handleClick();
}