package io.github.se7enx230.pielauncher

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppPickerPanel(
    apps: List<AppInfo>,
    showRemove: Boolean,
    onDismiss: () -> Unit,
    onAppSelected: (AppInfo?) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onDismiss()
            },
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .fillMaxHeight(0.80f)
                .background(
                    color = Color.Black.copy(alpha = 0.78f),
                    shape = RoundedCornerShape(24.dp)
                )
                .clickable(enabled = true) { }
                .padding(16.dp)
        ) {

            AppList(
                apps = apps,
                showRemove = showRemove,
                onAppSelected = onAppSelected
            )
        }
    }
}
