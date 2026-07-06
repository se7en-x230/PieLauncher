package io.github.se7enx230.pielauncher

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PieMenu(
    state: PieState,
    icons: List<ImageBitmap?>
) {

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        val radius = PieConstants.Radius.toPx()
        val iconSize = PieConstants.IconSize.toPx()
        val selectionRadius = PieConstants.SelectionRadius.toPx()
        val deadZoneRadius = PieConstants.DeadZoneRadius.toPx()

        drawCircle(
            color = Color.White,
            radius = radius,
            center = state.center,
            style = Stroke(width = 3.dp.toPx())
        )

        drawSpokes(
            center = state.center,
            radius = radius,
            count = PieConstants.SliceCount
        )

        if (state.selectedSlice == -1) {

            drawCircle(
                color = Color.White,
                radius = deadZoneRadius,
                center = state.center
            )

            if (state.editMode) {

                drawContext.canvas.nativeCanvas.drawText(
                    "E",
                    state.center.x,
                    state.center.y + 12f,
                    Paint().apply {
                        color = android.graphics.Color.BLACK
                        textAlign = Paint.Align.CENTER
                        textSize = 40f
                        isAntiAlias = true
                    }
                )
            }
        }

        repeat(PieConstants.SliceCount) { slice ->

            val iconCenter = PieLayout.sliceCenter(
                center = state.center,
                radius = radius,
                slice = slice,
                count = PieConstants.SliceCount
            )

            val icon = icons.getOrNull(slice)

            if (icon != null) {

                if (slice == state.selectedSlice) {

                    drawCircle(
                        color = Color.White,
                        radius = selectionRadius,
                        center = iconCenter,
                        style = Stroke(width = 3.dp.toPx())
                    )
                }

                drawImage(
                    image = icon,
                    srcOffset = IntOffset.Zero,
                    srcSize = IntSize(
                        icon.width,
                        icon.height
                    ),
                    dstOffset = IntOffset(
                        (iconCenter.x - iconSize / 2).toInt(),
                        (iconCenter.y - iconSize / 2).toInt()
                    ),
                    dstSize = IntSize(
                        iconSize.toInt(),
                        iconSize.toInt()
                    )
                )
            }
        }
    }
}

private fun DrawScope.drawSpokes(
    center: Offset,
    radius: Float,
    count: Int
) {

    repeat(count) { i ->

        val angle =
            Math.toRadians((360.0 / count) * i - 90.0)

        val end = Offset(
            x = center.x + radius * cos(angle).toFloat(),
            y = center.y + radius * sin(angle).toFloat()
        )

        drawLine(
            color = Color.White,
            start = center,
            end = end,
            strokeWidth = 3.dp.toPx()
        )
    }
}
