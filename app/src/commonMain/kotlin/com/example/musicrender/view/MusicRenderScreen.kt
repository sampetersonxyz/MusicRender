package com.example.musicrender.view

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.musicrender.model.GuitarFingering

@Composable
fun MusicRender() {
    Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // Now ChordRender and GuitarFingering are correctly detected
        ChordRender(
            chordName = "C Major",
            fingering = GuitarFingering(listOf(null, 3, 2, 0, 1, 0))
        )
    }
}
