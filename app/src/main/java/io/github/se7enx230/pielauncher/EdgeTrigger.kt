package io.github.se7enx230.pielauncher

import androidx.compose.ui.geometry.Offset

object EdgeTrigger {

    // Width of the active edge in pixels for now.
    // Later we'll make this configurable and use dp.
    private const val EDGE_WIDTH = 40f

    fun accepts(point: Offset): Boolean {
        return point.x <= EDGE_WIDTH
    }
}
