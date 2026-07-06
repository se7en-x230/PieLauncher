package io.github.se7enx230.pielauncher

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext

@Composable
fun PieOverlay() {

    val context = LocalContext.current

    val controller = remember {
        PieController()
    }

    var configuration by remember {
        mutableStateOf(
            ConfigurationStore.load(context)
        )
    }

    // -1 means no slice is currently being edited.
    var editingSlice by remember {
        mutableIntStateOf(-1)
    }

    val apps = remember {
        AppRegistry.installedApps(context)
    }

    LaunchedEffect(Unit) {

        apps.forEach { app ->
            Log.d(
                "PieOverlay",
                "${app.label} : ${app.packageName}"
            )
        }

        // Temporary until long-press is implemented.
        controller.enterEditMode()
    }

    val icons = remember(configuration) {

        List(PieConstants.SliceCount) { slice ->

            configuration.slices[slice]?.let { app ->
                IconLoader.load(
                    context,
                    app
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {

                detectDragGestures(

                    onDragStart = { offset ->
                        controller.fingerDown(offset)
                    },

                    onDrag = { change, _ ->
                        controller.fingerMove(change.position)
                    },

                    onDragEnd = {

                        val slice = controller.selectedSlice()

                        if (slice == -1) {
                            return@detectDragGestures
                        }

                        if (controller.state.editMode) {

                            editingSlice = slice

                            Log.d(
                                "PieOverlay",
                                "Editing slice: $editingSlice"
                            )

                            return@detectDragGestures
                        }

                        configuration.slices
                            .getOrNull(slice)
                            ?.let { app ->
                                Launcher.launch(
                                    context,
                                    app
                                )
                            }
                    }
                )
            }
    ) {

        PieMenu(
            state = controller.state,
            icons = icons
        )

        // Dialog will be connected in the next step.
    }
}
