package io.github.se7enx230.pielauncher

import androidx.compose.ui.graphics.StrokeCap
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
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
    ) {

        val iconSize = PieConstants.IconSize.toPx()
        val deadZoneRadius = PieConstants.DeadZoneRadius.toPx()


        repeat(FanSlots.SlotCount) { slot ->

            val slotCenter = FanSlots.position(
                center = state.center,
                slot = slot,
                layout = layout
            )


val icon = icons.getOrNull(slot)

if (icon != null) {

    val drawSize =
        if (slot == state.selectedSlice)
            iconSize * 1.6f
        else
            iconSize

    drawImage(
        image = icon,
        srcOffset = IntOffset.Zero,
        srcSize = IntSize(
            icon.width,
            icon.height
        ),
        dstOffset = IntOffset(
            (slotCenter.x - drawSize / 2).toInt(),
            (slotCenter.y - drawSize / 2).toInt()
        ),
        dstSize = IntSize(
            drawSize.toInt(),
            drawSize.toInt()
        )
    )
}        }
    }
}
