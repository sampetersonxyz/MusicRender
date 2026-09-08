import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.CanvasBasedWindow
import com.example.musicrender.view.MusicRender

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "chordCanvas") {
        // Wrap in a Surface to ensure the background is black within Compose
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            MusicRender()
        }
    }
}
