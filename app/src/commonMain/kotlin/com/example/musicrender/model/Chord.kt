package com.example.musicrender.model

data class Chord (
    val root: Note,
    val type: ChordType,
    val useFlats: Boolean = false
){
    val notes: List<Note>
        get() = type.intervals.map { interval ->
            Note.fromSemitone(
                root.semitone + interval.semitones
            )
        }

    override fun toString(): String {
        return root.display(useFlats) + type.symbol
    }
}