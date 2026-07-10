package io.github.se7enx230.pielauncher

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.core.graphics.drawable.toBitmap
import androidx.activity.compose.BackHandler
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
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
    val haptic = LocalHapticFeedback.current
    val controller = remember {
        PieController()
    }

    var screenWidth by remember {
        mutableIntStateOf(0)
    }
    var lastSelectedSlice by remember {
         mutableIntStateOf(-1)
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
var fingerDown by remember {
    mutableStateOf(false)
}

var lastPosition by remember {
    mutableStateOf(Offset.Zero)
}

var longPressTriggered by remember {
    mutableStateOf(false)
}
var showLibrary by remember {
    mutableStateOf(false)
}

var openLibraryOnRelease by remember {
    mutableStateOf(false)
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
    }
LaunchedEffect(fingerDown) {

    if (!fingerDown)
        return@LaunchedEffect

    kotlinx.coroutines.delay(1500)

if (!fingerDown)
    return@LaunchedEffect

val slice = controller.selectedSlice()

if (slice != -1) {

    editingSlot = slice
    showLibrary = true
    longPressTriggered = true

} else if (controller.isInCenter(lastPosition)) {

    showLibrary = true
    longPressTriggered = true

} else {

    WallpaperLauncher.open(context)
    longPressTriggered = true
}
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
BackHandler(enabled = showLibrary) {
    showLibrary = false
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
lastSelectedSlice = -1
                        controller.layout =
                            if (offset.x < screenWidth / 2f)
                                LauncherLayout.LEFT_HAND
                            else
                                LauncherLayout.RIGHT_HAND

                        controller.fingerDown(
                            offset,
                            screenHeight.toFloat()
                        )
fingerDown = true
longPressTriggered = false
                    },

                    onDrag = { change, _ ->

    lastPosition = change.position

    controller.fingerMove(
        change.position
    )

    val current = controller.selectedSlice()

    if (
        current != -1 &&
        current != lastSelectedSlice
    ) {

        haptic.performHapticFeedback(
            HapticFeedbackType.TextHandleMove
        )

        lastSelectedSlice = current
    }
},


onDragEnd = {
fingerDown = false
if (longPressTriggered) {

    longPressTriggered = false

    return@detectDragGestures
}
    if (openLibraryOnRelease) {

        openLibraryOnRelease = false
        showLibrary = true

        return@detectDragGestures
    }

val slot = controller.selectedSlice()

if (slot == -1) {
    (context as? android.app.Activity)?.finish()
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

        // (context as? android.app.Activity)?.finish()
    }                    }
                )
            }
    ) {

if (showLibrary) {

AppPickerPanel(
    apps = apps,
    showRemove = editingSlot != -1,

    onDismiss = {
        showLibrary = false
        editingSlot = -1
    },

onAppSelected = { app ->

    if (editingSlot == -1) {

        app?.let {

            Launcher.launch(
                context,
                it
            )

            // (context as? android.app.Activity)?.finish()
        }

    } else {

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
    }

    showLibrary = false
}
)

} else {

    FanMenu(
        state = controller.state,
        icons = icons,
        layout = controller.layout
    )
}

if (!showLibrary) {
    CenterButton(
        center = controller.state.center,
        onOpenLibrary = {
            showLibrary = true
        }
    )
}

}
}
