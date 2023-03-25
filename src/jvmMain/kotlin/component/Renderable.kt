package ru.nsu.ccfit.plum.component

import androidx.compose.runtime.Composable

interface Renderable {
    @Composable
    fun render();
}