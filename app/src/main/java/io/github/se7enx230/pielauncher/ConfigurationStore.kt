package io.github.se7enx230.pielauncher

import android.content.Context

object ConfigurationStore {

    private const val PREFS = "pie_launcher"
    private const val PROFILE_COUNT = 3

    private fun key(
        profile: Int,
        slot: Int
    ) = "profile_${profile}_slot_$slot"

    fun load(
        context: Context
    ): PieConfiguration {

        val prefs = context.getSharedPreferences(
            PREFS,
            Context.MODE_PRIVATE
        )

        val profiles = MutableList(PROFILE_COUNT) { profile ->

            val slots = MutableList<AppInfo?>(
                FanSlots.SlotCount
            ) { slot ->

                prefs.getString(
                    key(profile, slot),
                    null
                )?.let { packageName ->

                    AppRegistry.find(
                        context,
                        packageName
                    )
                }
            }

            Profile(
                slots = slots
            )
        }

        //
        // First run defaults
        //
        if (profiles.all { profile ->
                profile.slots.all { it == null }
            }) {

            val defaults =
                profiles[0].slots.toMutableList()

            AppRegistry.find(
                context,
                "eu.faircode.email"
            )?.let {
                defaults[0] = it
            }

            AppRegistry.find(
                context,
                "com.whatsapp"
            )?.let {
                defaults[1] = it
            }

            profiles[0] = Profile(defaults)

            val configuration =
                PieConfiguration(
                    profiles = profiles
                )

            save(
                context,
                configuration
            )

            return configuration
        }

        return PieConfiguration(
            profiles = profiles
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

            configuration.profiles.forEachIndexed { profileIndex, profile ->

                profile.slots.forEachIndexed { slotIndex, app ->

                    if (app == null) {

                        remove(
                            key(
                                profileIndex,
                                slotIndex
                            )
                        )

                    } else {

                        putString(
                            key(
                                profileIndex,
                                slotIndex
                            ),
                            app.packageName
                        )
                    }
                }
            }

            apply()
        }
    }
}
