package io.github.se7enx230.pielauncher

import androidx.compose.ui.geometry.Offset

data class PieState(
    val center: Offset = Offset.Zero,
    val selectedSlice: Int = -1
)
