package io.github.se7enx230.pielauncher

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset

@Composable
fun CenterButton(
    center: Offset,
    editMode: Boolean,
    onEnterEditMode: () -> Unit,
onExitEditMode: () -> Unit,
onOpenLibrary: () -> Unit
) {

    val radius = PieConstants.DeadZoneRadius

    val radiusPx = with(LocalDensity.current) {
        radius.toPx()
    }

    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    (center.x - radiusPx).toInt(),
                    (center.y - radiusPx).toInt()
                )
            }
            .size(
    if (editMode)
        radius
    else
        radius / 6
)
.background(
    if (editMode)
        Color.White
    else
        Color.White.copy(alpha = 0.35f),
    CircleShape
)
.combinedClickable(
    onClick = {
        // No action.
        // Releasing the finger will close the pie.
    },
    onLongClick = {
        onOpenLibrary()
    }
),
        contentAlignment = Alignment.Center
    ) {

    }
}
