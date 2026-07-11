package io.github.se7enx230.pielauncher

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppList(
    apps: List<AppInfo>,
    onAppSelected: (AppInfo?) -> Unit
) {

LazyColumn(
    modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 64.dp)
) {

    items(
        apps.sortedBy { it.label.lowercase() }
    ) { app ->

        Text(                text = app.label,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onAppSelected(app)
                    }
                    .padding(
                        horizontal = 20.dp,
                        vertical = 12.dp
                    )
            )
        }
    }
}
