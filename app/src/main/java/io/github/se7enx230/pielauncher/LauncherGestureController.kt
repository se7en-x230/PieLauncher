package io.github.se7enx230.pielauncher

import androidx.compose.ui.geometry.Offset

class LauncherGestureController {

    private var downPosition = Offset.Zero

    var pointerIsDown = false
        private set

    var dragging = false
        private set

    fun onDown(position: Offset) {

        pointerIsDown = true
        dragging = false
        downPosition = position
    }

    fun onMove(position: Offset) {

        if (!pointerIsDown)
            return

        dragging = true
    }

    fun onUp() {

        pointerIsDown = false
        dragging = false
    }

    fun downPosition(): Offset =
        downPosition
}
