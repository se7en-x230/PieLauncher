package io.github.se7enx230.pielauncher

data class Profile(

    val slots: List<AppInfo?> =
        List(FanSlots.SlotCount) { null }
)
