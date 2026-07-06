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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext

@Composable
fun PieOverlay() {

    val context = LocalContext.current

    val controller = remember {
        PieController()
    }

    var screenWidth by remember {
        mutableIntStateOf(0)
    }

    var screenHeight by remember {
        mutableIntStateOf(0)
    }

    var configuration by remember {
        mutableStateOf(
            ConfigurationStore.load(context)
        )
    }

    var editingSlot by remember {
        mutableIntStateOf(-1)
    }

    val apps = remember {
        AppRegistry.installedApps(context)
    }

    LaunchedEffect(Unit) {

        apps.forEach {
            Log.d(
                "PieOverlay",
                "${it.label} : ${it.packageName}"
            )
        }

        controller.enterEditMode()
    }

    val icons = List(FanSlots.SlotCount) { slot ->

        configuration
            .profiles[controller.currentProfile]
            .slots[slot]
            ?.let {
                IconLoader.load(
                    context,
                    it
                )
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged {

                screenWidth = it.width
                screenHeight = it.height
            }
            .pointerInput(
                screenWidth,
                screenHeight
            ) {

                detectDragGestures(

                    onDragStart = { offset ->

                        controller.layout =
                            if (offset.x < screenWidth / 2f)
                                LauncherLayout.LEFT_HAND
                            else
                                LauncherLayout.RIGHT_HAND

                        controller.fingerDown(
                            offset,
                            screenHeight.toFloat()
                        )
                    },

                    onDrag = { change, _ ->

                        controller.fingerMove(
                            change.position
                        )
                    },

                    onDragEnd = {

                        val slot =
                            controller.selectedSlice()

                        if (slot == -1)
                            return@detectDragGestures

                        if (controller.state.editMode) {

                            editingSlot = slot
                            return@detectDragGestures
                        }

                        configuration
                            .profiles[controller.currentProfile]
                            .slots[slot]
                            ?.let {

                                Launcher.launch(
                                    context,
                                    it
                                )
                            }
                    }
                )
            }
    ) {

        FanMenu(
            state = controller.state,
            icons = icons,
            layout = controller.layout
        )

        if (editingSlot != -1) {

            AppChooserDialog(

                apps = apps,

                onDismiss = {

                    editingSlot = -1

                    // Stay in edit mode while developing.
                    controller.enterEditMode()
                },

                onAppSelected = { app ->

                    val profiles =
                        configuration.profiles.toMutableList()

                    val slots =
                        profiles[controller.currentProfile]
                            .slots
                            .toMutableList()

                    slots[editingSlot] = app

                    profiles[controller.currentProfile] =
                        Profile(slots)

                    val newConfiguration =
                        configuration.copy(
                            profiles = profiles
                        )

                    configuration = newConfiguration

                    ConfigurationStore.save(
                        context,
                        newConfiguration
                    )

                    editingSlot = -1

                    // Stay in edit mode while developing.
                    controller.enterEditMode()
                }
            )
        }
    }
}
