package io.github.se7enx230.pielauncher

import androidx.compose.ui.geometry.Offset
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

object FanSlots {

    const val SlotCount = 6

    private const val Radius = 230f

    /*
     * Thumb fan.
     *
     * The fan always wraps around the thumb.
     *
     * RIGHT_HAND
     *
     *      (
     *
     * LEFT_HAND
     *
     *      )
     */

    // Degrees from the horizontal.
    // Negative = above trigger.
    // Positive = below trigger.
    private val angles = listOf(
        -65f,
        -40f,
        -18f,
         18f,
         40f,
         65f
    )

    fun position(
        center: Offset,
        slot: Int,
        layout: LauncherLayout
    ): Offset {

        val radians = angles[slot] * PI.toFloat() / 180f

        val dx = cos(radians) * Radius
        val dy = sin(radians) * Radius

        return when (layout) {

            LauncherLayout.RIGHT_HAND ->

                Offset(
                    x = center.x - dx,
                    y = center.y + dy
                )

            LauncherLayout.LEFT_HAND ->

                Offset(
                    x = center.x + dx,
                    y = center.y + dy
                )
        }
    }

    fun closest(
        finger: Offset,
        center: Offset,
        layout: LauncherLayout
    ): Int {

        var best = -1
        var bestDistance = Float.MAX_VALUE

        repeat(SlotCount) { slot ->

            val p = position(
                center,
                slot,
                layout
            )

            val d = hypot(
                (finger.x - p.x).toDouble(),
                (finger.y - p.y).toDouble()
            ).toFloat()

            if (d < bestDistance) {
                bestDistance = d
                best = slot
            }
        }

        return best
    }
}
