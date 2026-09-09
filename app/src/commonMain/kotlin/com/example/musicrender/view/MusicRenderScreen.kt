package com.example.musicrender.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.musicrender.model.Chord
import com.example.musicrender.model.ChordType
import com.example.musicrender.model.GuitarChordGenerator
import com.example.musicrender.model.GuitarFingering
import com.example.musicrender.model.Note

@Composable
fun MusicRender() {

    var noteSelection by remember { mutableStateOf(Note.G) }
    var typeSelection by remember { mutableStateOf(ChordType.MAJOR) }
    val chord = Chord(noteSelection, typeSelection)

    val generator = GuitarChordGenerator()
    val fingerings = generator.generateOpenFingerings(chord)

    Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // Now ChordRender and GuitarFingering are correctly detected
        Row {
            EnumDropdown(
                label = "Note",
                options = Note.entries.toTypedArray(),
                selected = noteSelection,
                onSelected = { noteSelection = it }
            )
            EnumDropdown(
                label = "Type",
                options = ChordType.entries.toTypedArray(),
                selected = typeSelection,
                onSelected = { typeSelection = it }
            )
        }
        ChordRender(
            chordName = noteSelection.toString() + " " + typeSelection.toString(),
            fingering = fingerings[0]
        )
    }
}
@Composable
fun <T> EnumDropdown(
    label: String,
    options: Array<T>,
    selected: T,
    onSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(
            onClick = { expanded = true },
            modifier = Modifier.border(
                width = 2.dp,
                color = Color.White,
                shape = RectangleShape
            ),
            colors = ButtonDefaults.buttonColors(
//                containerColor = Color(0xFF1565C0),
                containerColor = Color.Black,
                contentColor = Color.White,
            ),
            shape = RectangleShape,
//            shape = RoundedCornerShape(8.dp)
        ) {
            Text("$label: $selected")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF222222))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option.toString(),
                            color = Color.White
                        )
                    },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}