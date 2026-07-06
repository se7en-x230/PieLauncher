package io.github.se7enx230.pielauncher

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun CenterButton(
    center: Offset,
    editMode: Boolean,
    profileName: String,
    onLongPress: () -> Unit
) {

    Canvas(

        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {

                detectTapGestures(

                    onLongPress = {

                        onLongPress()
                    }
                )
            }
    ) {

        drawCircle(
            color = Color.White,
            radius = PieConstants.DeadZoneRadius.toPx(),
            center = center
        )

        drawContext.canvas.nativeCanvas.drawText(

            if (editMode) "E" else "",

            center.x,
            center.y + 8f,

            Paint().apply {

                color = android.graphics.Color.BLACK
                textAlign = Paint.Align.CENTER
                textSize = 40f
                isAntiAlias = true
            }
        )

        drawContext.canvas.nativeCanvas.drawText(

            profileName,

            center.x,
            center.y + 42f,

            Paint().apply {

                color = android.graphics.Color.WHITE
                textAlign = Paint.Align.CENTER
                textSize = 24f
                isAntiAlias = true
            }
        )
    }
}
