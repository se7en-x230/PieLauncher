package io.github.se7enx230.pielauncher

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

    var layout by mutableStateOf(
        LauncherLayout.RIGHT_HAND
    )

    var currentProfile by mutableIntStateOf(0)
        private set

    var mode by mutableStateOf(
        LauncherMode.NORMAL
    )
        private set

    fun fingerDown(
        position: Offset,
        screenHeight: Float
    ) {

        currentProfile =
            when {

                position.y < screenHeight / 3f -> 0

                position.y < screenHeight * 2f / 3f -> 1

                else -> 2
            }

        state = state.copy(
            center = position,
            selectedSlice = -1,
            editMode = mode == LauncherMode.EDIT
        )
    }

    fun fingerMove(
        position: Offset
    ) {

        val distance = hypot(
            (position.x - state.center.x).toDouble(),
            (position.y - state.center.y).toDouble()
        ).toFloat()

        if (distance < DEAD_ZONE_RADIUS) {

            state = state.copy(
                selectedSlice = -1
            )

            return
        }

        val slot = FanSlots.closest(
            finger = position,
            center = state.center,
            layout = layout
        )

        state = state.copy(
            selectedSlice = slot
        )
    }

    fun selectedSlice(): Int =
        state.selectedSlice

    fun enterEditMode() {

        mode = LauncherMode.EDIT

        state = state.copy(
            editMode = true,
            selectedSlice = -1
        )
    }

    fun exitEditMode() {

        mode = LauncherMode.NORMAL

        state = state.copy(
            editMode = false,
            selectedSlice = -1
        )
    }

    fun toggleEditMode() {

        if (mode == LauncherMode.EDIT)
            exitEditMode()
        else
            enterEditMode()
    }

    val isEditMode: Boolean
        get() = mode == LauncherMode.EDIT
}
