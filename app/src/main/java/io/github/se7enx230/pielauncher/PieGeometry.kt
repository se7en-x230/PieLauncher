package io.github.se7enx230.pielauncher

import androidx.compose.ui.geometry.Offset
import kotlin.math.atan2

object PieGeometry {

    fun angle(
        center: Offset,
        point: Offset
    ): Float {

        val dx = point.x - center.x
        val dy = point.y - center.y

        var degrees = Math.toDegrees(
            atan2(dy, dx).toDouble()
        ).toFloat()

        degrees += 90f

        if (degrees < 0f)
            degrees += 360f

        return degrees
    }

    fun slice(
        angle: Float,
        count: Int
    ): Int {

        val sliceSize = 360f / count

        return (angle / sliceSize).toInt() % count
    }

    fun startAngle(
        slice: Int,
        count: Int
    ): Float {

        return slice * (360f / count) - 90f
    }

    fun sweepAngle(
        count: Int
    ): Float {

        return 360f / count
    }
}
