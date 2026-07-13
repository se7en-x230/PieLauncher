package io.github.se7enx230.pielauncher

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log

object Launcher {

    fun launch(
        context: Context,
        app: AppInfo
    ): Boolean {

        return try {
            val intent = Intent().apply {

                component = ComponentName(
                    app.packageName,
                    app.activityName
                )

                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            context.startActivity(intent)
            
            // Track usage
            ConfigurationStore.incrementUsageCount(context, app.packageName)
            
            true

        } catch (e: Exception) {
            Log.e("Launcher", "Failed to launch ${app.label}", e)
            false
        }
    }
}
