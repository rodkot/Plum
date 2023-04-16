package ru.nsu.ccfit.plum.tool.filter

import kotlin.math.max
import kotlin.math.min

fun Int.red() = this shr 16 and 0xFF

fun Int.green() = this shr 8 and 0xFF

fun Int.blue() = this and 0xFF

fun getNewColor(
    newR: Int,
    newG: Int,
    newB: Int
) = 0xFF shl 24 or (newR shl 16) or (newG shl 8) or newB

fun com(neighbourColorComponent: Int) = max(min(neighbourColorComponent, 0xFF), 0)