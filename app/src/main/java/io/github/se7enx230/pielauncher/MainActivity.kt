package io.github.se7enx230.pielauncher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.se7enx230.pielauncher.ui.theme.PieLauncherTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PieLauncherTheme {
                PieOverlay()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PieLauncherPreview() {
    PieLauncherTheme {
        PieOverlay()
    }
}
