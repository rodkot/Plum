package ru.nsu.ccfit.plum.icon

import androidx.compose.runtime.Composable

/**
 * Класс отображения иконки
 */
abstract class Icon(){

    /**
     * Функция возращающая отображение иконки в зависимости от состояния иконки
     * @param checked состояние иконки:
     * - true выбрана
     * - false отжата
     */
    @Composable
    abstract fun get(checked: Boolean)
}