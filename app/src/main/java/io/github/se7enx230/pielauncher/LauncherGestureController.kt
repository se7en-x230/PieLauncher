package io.github.se7enx230.pielauncher

import androidx.compose.ui.geometry.Offset
import kotlin.math.hypot

class LauncherGestureController {

    companion object {
        private const val DRAG_THRESHOLD = 16f
    }

    private var downPosition = Offset.Zero

    var pointerDown = false
        private set

    var dragStarted = false
        private set

    var longPressTriggered = false
        private set

    fun onDown(position: Offset) {

        pointerDown = true
        dragStarted = false
        longPressTriggered = false
        downPosition = position
    }

    fun onMove(position: Offset) {

        if (!pointerDown)
            return

        if (dragStarted)
            return

        val distance = hypot(
            (position.x - downPosition.x).toDouble(),
            (position.y - downPosition.y).toDouble()
        ).toFloat()

        if (distance > DRAG_THRESHOLD) {
            dragStarted = true
        }
    }

    fun triggerLongPress() {

        longPressTriggered = true
    }

    fun onUp() {

        pointerDown = false
        dragStarted = false
        longPressTriggered = false
    }

    val isDragging: Boolean
        get() = dragStarted

    val isPressed: Boolean
        get() = pointerDown

    val pressPosition: Offset
        get() = downPosition
}
