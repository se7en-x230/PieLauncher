package io.github.se7enx230.pielauncher

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

object IconLoader {

    private val cache = mutableMapOf<String, ImageBitmap>()
    private const val MAX_CACHE_SIZE = 100

    fun clearCache() {
        cache.clear()
    }

    fun load(
        context: Context,
        app: AppInfo
    ): ImageBitmap? {

        cache[app.packageName]?.let {
            return it
        }

        return try {

            val drawable =
                context.packageManager.getApplicationIcon(
                    app.packageName
                )

            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth.coerceAtLeast(1),
                drawable.intrinsicHeight.coerceAtLeast(1),
                Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(bitmap)

            drawable.setBounds(
                0,
                0,
                canvas.width,
                canvas.height
            )

            drawable.draw(canvas)

            val image = bitmap.asImageBitmap()

            // Prevent unbounded cache growth
            if (cache.size >= MAX_CACHE_SIZE) {
                cache.remove(cache.keys.first())
            }

            cache[app.packageName] = image

            image

        } catch (_: Exception) {

            null

        }
    }
}
