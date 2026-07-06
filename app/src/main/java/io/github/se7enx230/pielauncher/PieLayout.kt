package io.github.se7enx230.pielauncher

import androidx.compose.ui.geometry.Offset
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object PieLayout {

    fun sliceCenter(
        center: Offset,
        radius: Float,
        slice: Int,
        count: Int
    ): Offset {

        val angle =
            ((360f / count) * slice - 90f) + (180f / count)

        val radians = angle * PI / 180.0

        return Offset(
            x = center.x + (radius * 0.65f * cos(radians)).toFloat(),
            y = center.y + (radius * 0.65f * sin(radians)).toFloat()
        )
    }
}
