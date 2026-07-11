package io.github.se7enx230.pielauncher

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import kotlin.math.hypot


class PieController {

companion object {
    private const val DEAD_ZONE_RADIUS = 72f
    private const val ACTIVATION_RADIUS = 160f
}
    var state by mutableStateOf(PieState())
        private set

    var layout by mutableStateOf(
        LauncherLayout.RIGHT_HAND
    )

    var currentProfile by mutableIntStateOf(0)
        private set

    fun reset() {
        state = PieState()
        currentProfile = 0
    }

    fun fingerDown(
        position: Offset,
        screenHeight: Float
    ) {
currentProfile =
    when {

        // Top profile: upper 45% of the screen
        position.y < screenHeight * 0.45f -> 0

        // Middle profile: 45% - 80%
        position.y < screenHeight * 0.70f -> 1

        // Bottom profile: last 20%
        else -> 2
    }
state = state.copy(
    center = position,
    selectedSlice = -1,
    editMode = false
)
    }
fun isInCenter(position: Offset): Boolean =
    hypot(
        (position.x - state.center.x).toDouble(),
        (position.y - state.center.y).toDouble()
    ) < DEAD_ZONE_RADIUS
fun fingerMove(
    position: Offset
) {

    val distance = hypot(
        (position.x - state.center.x).toDouble(),
        (position.y - state.center.y).toDouble()
    ).toFloat()

    if (distance < ACTIVATION_RADIUS) {

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
}
