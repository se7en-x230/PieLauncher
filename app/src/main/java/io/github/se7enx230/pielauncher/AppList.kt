package io.github.se7enx230.pielauncher

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppList(
    apps: List<AppInfo>,
    onAppSelected: (AppInfo?) -> Unit
) {

    var query by remember { mutableStateOf("") }

    val filteredApps = remember(apps, query) {
        apps
            .sortedBy { it.label.lowercase() }
            .filter {
                it.label.contains(query, ignoreCase = true)
            }
    }

    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 26.dp)
    ) {

        item {

            BasicTextField(
                value = query,
                onValueChange = { query = it },

                singleLine = true,

                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 22.sp
                ),

                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),

                keyboardActions = KeyboardActions(
                    onSearch = {

                        focusManager.clearFocus()

                        filteredApps
                            .firstOrNull()
                            ?.let(onAppSelected)
                    }
                ),

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 12.dp
                    ),

                decorationBox = { innerTextField ->

                    Column {

                        if (query.isEmpty()) {

                            Text(
                                text = "🔍 Search...",
                                color = Color.Gray,
                                fontSize = 22.sp
                            )
                        }

                        innerTextField()

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        HorizontalDivider()
                    }
                }
            )
        }


        items(filteredApps) { app ->

            Text(
                text = app.label,
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
