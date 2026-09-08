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

        //Filter Fingerings
        val threeOrMore = mutableListOf<GuitarFingering>()
        for (f in fingerings) {
            var playedStringCount = 0
            var playedNoteCount = 0
            var containsNull = false
            for (fret in f.frets) {
                if(fret != null) {
                    playedStringCount ++
                    if(fret != 0) {
                        playedNoteCount ++
                    }
                } else {
                    containsNull = true
                }
            }
            var isMutedEdge = true
            var isMiddleMute = false
            for (i in 0..2) {
                if(f.frets[i] != null)  {
                    isMutedEdge = false
                }
                if (!isMutedEdge && f.frets[i] == null) {
                    isMiddleMute = true
                }
            }
            isMutedEdge = true
            if(!isMiddleMute) {
                for (i in listOf(5,4,3)) {
                    if(f.frets[i] != null)  {
                        isMutedEdge = false
//                        Log.d("TEST", "i "  + i + " mutededge " + isMutedEdge)
                    }
                    if (!isMutedEdge && f.frets[i] == null) {
                        isMiddleMute = true
//                        Log.d("TEST", "i "  + i + " middle " + isMiddleMute)
                    }
                }
            }

            if(playedStringCount > 3 && playedNoteCount < 5 && !isMiddleMute) {
                threeOrMore.add(f)
            }
        }

        var maxStrings = 0
        for (chord in threeOrMore) {
            if(chord.playedStrings() > maxStrings) {
                maxStrings = chord.playedStrings()
            }
        }
        val maxList = mutableListOf<GuitarFingering>()
        for(chord in threeOrMore) {
            if(chord.playedStrings() == maxStrings) {
                maxList.add(chord)
            }
        }


//        Log.d("TEST", listOf(
//            "SHOW ME CHORD",
//            CChord,
//            CChord.notes,
//            maxList,
//        ).toString())

//        chordList.value = maxList

        return maxList

    }
}