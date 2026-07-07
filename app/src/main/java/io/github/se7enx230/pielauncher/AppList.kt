package io.github.se7enx230.pielauncher

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppList(
    apps: List<AppInfo>,
    showRemove: Boolean,
    onAppSelected: (AppInfo?) -> Unit
) {

    var query by remember {
        mutableStateOf("")
    }

    val filteredApps = remember(apps, query) {
        apps
            .sortedBy { it.label.lowercase() }
            .filter {
                it.label.contains(
                    query,
                    ignoreCase = true
                )
            }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 16.dp,
                bottom = 16.dp
            )
    ) {

        item {
val focusRequester = remember {
    FocusRequester()
}

LaunchedEffect(Unit) {
    focusRequester.requestFocus()
}
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                singleLine = true,
                label = {
                    Text("Search")
                },
modifier = Modifier
    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
            )

            HorizontalDivider()
        }

        if (showRemove) {

            item {

                Text(
                    text = "None",
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
        }

        items(filteredApps) { app ->

            Text(
                text = app.label,
                fontSize = 20.sp,
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
