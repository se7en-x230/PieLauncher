package io.github.se7enx230.pielauncher

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun AppChooserDialog(
    apps: List<AppInfo>,
    showRemove: Boolean,
    onDismiss: () -> Unit,
    onAppSelected: (AppInfo?) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text(
                text = "Apps",
                fontSize = 20.sp
            )
        },

        text = {
            AppList(
                apps = apps,
                showRemove = showRemove,
                onAppSelected = onAppSelected
            )
        },

        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}
