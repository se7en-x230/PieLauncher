package io.github.se7enx230.pielauncher

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppList(
    apps: List<AppInfo>,
    showRemove: Boolean,
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

    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboard?.show()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {

        item {
val focusManager = LocalFocusManager.current
OutlinedTextField(
    value = query,
    onValueChange = { query = it },

    singleLine = true,

    label = {
        Text("Search")
    },

    keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Search
    ),

    keyboardActions = KeyboardActions(

onSearch = {

    focusManager.clearFocus()

    filteredApps
        .firstOrNull()
        ?.let {
            onAppSelected(it)
        }
}    ),

    modifier = Modifier
        .fillMaxWidth()
        .focusRequester(focusRequester)
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
