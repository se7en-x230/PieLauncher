package io.github.se7enx230.pielauncher

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import kotlin.math.hypot

class PieController {

    companion object {
        private const val DEAD_ZONE_RADIUS = 48f
    }

    var state by mutableStateOf(PieState())
        private set

    fun fingerDown(position: Offset) {
        state = state.copy(
            center = position,
            selectedSlice = -1
        )
    }

    fun fingerMove(position: Offset) {

        val dx = position.x - state.center.x
        val dy = position.y - state.center.y

        val distance = hypot(
            dx.toDouble(),
            dy.toDouble()
        ).toFloat()

        if (distance < DEAD_ZONE_RADIUS) {
            state = state.copy(
                selectedSlice = -1
            )
            return
        }

        val angle = PieGeometry.angle(
            center = state.center,
            point = position
        )

        state = state.copy(
            selectedSlice = PieGeometry.slice(
                angle,
                PieConstants.SliceCount
            )
        )
    }

    fun selectedSlice(): Int =
        state.selectedSlice

    fun enterEditMode() {
        state = state.copy(
            editMode = true,
            selectedSlice = -1
        )
    }

    fun exitEditMode() {
        state = state.copy(
            editMode = false,
            selectedSlice = -1
        )
    }

    fun toggleEditMode() {
        if (state.editMode) {
            exitEditMode()
        } else {
            enterEditMode()
        }
    }
}
