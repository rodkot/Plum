package ru.nsu.ccfit.plum.tool

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import ru.nsu.ccfit.plum.component.PlumImage

abstract class Tool(val name: String) {

    //TODO: Кастыль для работы применения фильтра при изменении его параметров
    var check = mutableStateOf(true)

    /**
     * Функция рисующая инсрументом
     * @param image исходное изображение
     * @param pressOffset точка нсжатия инструмента на изображение
     * @param releaseOffset точка отпускания кнопки на изображении
     * @param size размер окна на котором отображается изображение
     * @return возвращает изображение с приминеным инструментом
     */
    abstract fun draw(
        image: PlumImage,
        pressOffset: Offset = Offset.Zero,
        releaseOffset: Offset = Offset.Zero,
        size: IntSize = IntSize.Zero
    ): PlumImage
}