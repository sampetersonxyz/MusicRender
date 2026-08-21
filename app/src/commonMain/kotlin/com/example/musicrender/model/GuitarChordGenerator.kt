package com.example.musicrender.model

class GuitarChordGenerator {

    val tuning = listOf(
        Note.E,
        Note.A,
        Note.D,
        Note.G,
        Note.B,
        Note.E
    )

    val maxFret = 4

    fun fretsForNoteOnString(
        note: Note,
        stringIndex: Int,
        maxFret: Int = 4
    ): List<Int> {
        val openNote = tuning[stringIndex]
        val diff = (note.semitone - openNote.semitone + 12) % 12
        val positions = mutableListOf<Int>()
        var fret = diff
        while (fret <= maxFret) {
            positions.add(fret)
            fret += 12
        }
        return positions
    }

    fun generateOpenFingerings(
        chord: Chord,
        stringIndex: Int = 0,
        currentFingering: MutableList<Int?> = mutableListOf(),
    ): List<GuitarFingering> {

        if(stringIndex == 6) {
            return listOf(
                GuitarFingering(
                    currentFingering.toList()
                )
            )
        }

        val fingerings = mutableListOf<GuitarFingering>()

        //test muted string
        currentFingering.add(null)
        fingerings += generateOpenFingerings(
            chord,
            stringIndex + 1,
            currentFingering
        )
        currentFingering.removeAt(currentFingering.lastIndex)

        for (note in chord.notes) {
            for (fret in fretsForNoteOnString(note, stringIndex)) {
                currentFingering.add(fret)
                fingerings += generateOpenFingerings(
                    chord,
                    stringIndex + 1,
                    currentFingering
                )
                currentFingering.removeAt(currentFingering.lastIndex)
            }
        }
        return fingerings

    }
}