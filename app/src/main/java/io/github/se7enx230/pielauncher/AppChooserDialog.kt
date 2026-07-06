package io.github.se7enx230.pielauncher

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppChooserDialog(
    apps: List<AppInfo>,
    onDismiss: () -> Unit,
    onAppSelected: (AppInfo) -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text("Choose app")
        },

        text = {

            LazyColumn {

                items(
                    apps.sortedBy { it.label.lowercase() }
                ) { app ->

                    Text(
                        text = app.label,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onAppSelected(app)
                            }
                    )
                }
            }
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
