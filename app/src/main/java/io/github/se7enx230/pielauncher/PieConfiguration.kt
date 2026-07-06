package io.github.se7enx230.pielauncher

data class PieConfiguration(

    val profiles: List<Profile> =
        List(3) { Profile() }
)
