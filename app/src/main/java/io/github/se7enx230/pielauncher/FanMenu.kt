package io.github.se7enx230.pielauncher

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun FanMenu(
    state: PieState,
    icons: List<ImageBitmap?>,
    layout: LauncherLayout = LauncherLayout.RIGHT_HAND
) {

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        val iconSize = PieConstants.IconSize.toPx()
        val selectionRadius = PieConstants.SelectionRadius.toPx()
        val deadZoneRadius = PieConstants.DeadZoneRadius.toPx()

        // Dead zone
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

        repeat(FanSlots.SlotCount) { slot ->

            val slotCenter = FanSlots.position(
                center = state.center,
                slot = slot,
                layout = layout
            )

            if (slot == state.selectedSlice) {

                drawLine(
                    color = Color.White,
                    start = state.center,
                    end = slotCenter,
                    strokeWidth = 2.dp.toPx()
                )

                drawCircle(
                    color = Color.Yellow,
                    radius = selectionRadius,
                    center = slotCenter,
                    style = Stroke(
                        width = 3.dp.toPx()
                    )
                )
            }

            val icon = icons.getOrNull(slot)

            if (icon != null) {

                drawImage(
                    image = icon,
                    srcOffset = IntOffset.Zero,
                    srcSize = IntSize(
                        icon.width,
                        icon.height
                    ),
                    dstOffset = IntOffset(
                        (slotCenter.x - iconSize / 2).toInt(),
                        (slotCenter.y - iconSize / 2).toInt()
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
