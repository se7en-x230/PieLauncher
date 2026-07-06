package io.github.se7enx230.pielauncher

import android.content.Context
import android.content.Intent

object AppRegistry {

    fun installedApps(
        context: Context
    ): List<AppInfo> {

        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        return context.packageManager
            .queryIntentActivities(intent, 0)
            .map {

                AppInfo(
                    label = it.loadLabel(context.packageManager).toString(),
                    packageName = it.activityInfo.packageName,
                    activityName = it.activityInfo.name
                )

            }
            .sortedBy {
                it.label.lowercase()
            }
    }

    fun find(
        context: Context,
        packageName: String
    ): AppInfo? {

        return installedApps(context).firstOrNull {
            it.packageName == packageName
        }
    }
}
