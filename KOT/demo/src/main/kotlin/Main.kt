import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.graphics.Color

@Composable
@Preview
fun App() {
    var counter by remember { mutableStateOf(0) }

    val colors = arrayOf(
        Color(0xFFFF0000), // Red
        Color(0xFFFF7F00), // Orange
        Color(0xFFFFFF00), // Yellow
        Color(0xFF00FF00), // Green
        Color(0xFF0000FF), // Blue
        Color(0xFF4B0082), // Indigo
        Color(0xFF8B00FF), // Violet
        Color(0xFFFFA500), // Orange (lighter)
        Color(0xFF00CED1), // Dark Turquoise
        Color(0xFFFF1493), // Deep Pink
        Color(0xFF32CD32)  // Lime Green
    )


    MaterialTheme {
        Column {
            for (i in 0..10) {
                Button(
                    colors = ButtonDefaults.buttonColors(colors[i]),
                    onClick = {
                    counter = i
                }) {
                    Text(counter.toString())
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
