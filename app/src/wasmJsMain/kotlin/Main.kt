import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.example.musicrender.view.ChordRender
import com.example.musicrender.model.GuitarFingering

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "chordCanvas") {
        // Example: Rendering a C Major chord on the web
        ChordRender(
            chordName = "C Major",
            fingering = GuitarFingering(listOf(null, 3, 2, 0, 1, 0))
        )
    }
}
