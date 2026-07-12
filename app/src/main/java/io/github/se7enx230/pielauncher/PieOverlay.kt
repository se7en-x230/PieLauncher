package io.github.se7enx230.pielauncher

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.activity.compose.BackHandler
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.positionChange
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
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
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
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

var hasDragged by remember {
    mutableStateOf(false)
}

var showLibrary by remember {
    mutableStateOf(false)
}

    val apps = remember(context) {
        AppRegistry.installedApps(context)
    }

    LaunchedEffect(fingerDown, hasDragged) {

    if (!fingerDown)
        return@LaunchedEffect

    kotlinx.coroutines.delay(1500)

if (!fingerDown)
    return@LaunchedEffect

// Only trigger long-press if user hasn't dragged
if (!hasDragged) {
    // Long press without dragging - open wallpaper chooser
    WallpaperLauncher.open(context)
    longPressTriggered = true
}
}
    val icons = remember(configuration, controller.currentProfile) {
        List(FanSlots.SlotCount) { slot ->
            configuration
                .profiles
                .getOrNull(controller.currentProfile)
                ?.slots
                ?.getOrNull(slot)
                ?.let {
                    IconLoader.load(
                        context,
                        it
                    )
                }
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
                awaitEachGesture {
                    val down = awaitFirstDown()
                        val downPosition = down.position
                        
                        lastSelectedSlice = -1
                        hasDragged = false
                        
                        controller.layout =
                            if (downPosition.x < screenWidth / 2f)
                                LauncherLayout.LEFT_HAND
                            else
                                LauncherLayout.RIGHT_HAND

                        controller.fingerDown(
                            downPosition,
                            screenHeight.toFloat()
                        )
                        
                        fingerDown = true
                        longPressTriggered = false
                        lastPosition = downPosition

                        var dragStarted = false

                        do {
                            val event = awaitPointerEvent()
                            val change = event.changes.firstOrNull() ?: break
                            
                            if (change.positionChange() != Offset.Zero) {
                                hasDragged = true
                                dragStarted = true
                                
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
                            }
                            
                        } while (change.pressed)

                        // Finger released
                        fingerDown = false
                        
                        if (longPressTriggered) {
                            longPressTriggered = false
                            return@awaitEachGesture
                        }

                        val slot = controller.selectedSlice()

                        if (slot == -1) {
                            controller.reset()
                            return@awaitEachGesture
                        }
                        
                        configuration
                            .profiles[controller.currentProfile]
                            .slots[slot]
                            ?.let {

                                Launcher.launch(
                                    context,
                                    it
                                )

                                controller.reset()
                                (context as? android.app.Activity)?.finish()
                            }
                }
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

            // Reset controller state after launching
            controller.reset()
            
            // Optionally close the launcher activity
            (context as? android.app.Activity)?.finish()
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

    CenterButton(
        center = controller.state.center,
        onOpenLibrary = {
            showLibrary = true
        }
    )
}

}
}
