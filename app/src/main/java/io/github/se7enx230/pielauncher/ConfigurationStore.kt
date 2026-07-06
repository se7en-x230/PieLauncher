package io.github.se7enx230.pielauncher

import android.content.Context

object ConfigurationStore {

    private const val PREFS = "pie_launcher"
    private const val PREFIX = "slice_"

    fun load(
        context: Context
    ): PieConfiguration {

        val prefs = context.getSharedPreferences(
            PREFS,
            Context.MODE_PRIVATE
        )

        val slices = MutableList<AppInfo?>(PieConstants.SliceCount) {
            null
        }

        repeat(PieConstants.SliceCount) { slice ->

            val packageName = prefs.getString(
                PREFIX + slice,
                null
            ) ?: return@repeat

            slices[slice] =
                AppRegistry.find(
                    context,
                    packageName
                )
        }

        // First run defaults
        if (slices.all { it == null }) {

            AppRegistry.find(
                context,
                "eu.faircode.email"
            )?.let {
                slices[0] = it
            }

            AppRegistry.find(
                context,
                "com.whatsapp"
            )?.let {
                slices[1] = it
            }

            save(
                context,
                PieConfiguration(slices)
            )
        }

        return PieConfiguration(
            slices = slices
        )
    }

    fun save(
        context: Context,
        configuration: PieConfiguration
    ) {

        val prefs = context.getSharedPreferences(
            PREFS,
            Context.MODE_PRIVATE
        )

        prefs.edit().apply {

            configuration.slices.forEachIndexed { index, app ->

                if (app == null) {

                    remove(PREFIX + index)

                } else {

                    putString(
                        PREFIX + index,
                        app.packageName
                    )
                }
            }

            apply()
        }
    }
}
