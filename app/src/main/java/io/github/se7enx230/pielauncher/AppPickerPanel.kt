package io.github.se7enx230.pielauncher

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                .fillMaxWidth(0.75f)
                .fillMaxHeight(0.85f)
                .background(
                    Color.Black.copy(alpha = 0.78f),
                    RoundedCornerShape(24.dp)
                )
                .clickable(enabled = true) { }
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                if (showRemove) {

                    Text(
                        text = "No app",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onAppSelected(null)
                            }
                            .padding(
                                horizontal = 20.dp,
                                vertical = 12.dp
                            )
                    )

                    HorizontalDivider()
                }

Box(
    modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.90f)
) {
    AppList(
        apps = apps,
        onAppSelected = {
            onAppSelected(it)
        }
    )
}
                HorizontalDivider()

                Text(
                    text = "Cancel",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onDismiss()
                        }
                        .padding(
                            horizontal = 20.dp,
                            vertical = 14.dp
                        )
                )
            }
        }
    }
}
