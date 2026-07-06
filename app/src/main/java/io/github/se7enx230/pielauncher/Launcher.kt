package io.github.se7enx230.pielauncher

import android.content.ComponentName
import android.content.Context
import android.content.Intent

object Launcher {

    fun launch(
        context: Context,
        app: AppInfo
    ) {

        val intent = Intent().apply {

            component = ComponentName(
                app.packageName,
                app.activityName
            )

            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent)
    }
}
