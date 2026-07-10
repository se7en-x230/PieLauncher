package io.github.se7enx230.pielauncher

import android.content.Context
import android.content.Intent

object WallpaperLauncher {

    fun open(
        context: Context
    ) {

        val intent = Intent(
            Intent.ACTION_SET_WALLPAPER
        ).apply {

            addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
            )
        }

        context.startActivity(intent)
    }
}
