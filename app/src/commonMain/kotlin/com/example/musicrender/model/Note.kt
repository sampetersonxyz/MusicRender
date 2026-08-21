package com.example.musicrender.model

enum class Note(val semitone: Int) {
    C(0),
    C_SHARP(1),
    D(2),
    D_SHARP(3),
    E(4),
    F(5),
    F_SHARP(6),
    G(7),
    G_SHARP(8),
    A(9),
    A_SHARP(10),
    B(11);

    companion object {
        private val stringMap = mapOf(
            "C" to C, "C#" to C_SHARP, "Db" to C_SHARP,
            "D" to D, "D#" to D_SHARP, "Eb" to D_SHARP,
            "E" to E, "F" to F, "F#" to F_SHARP, "Gb" to F_SHARP,
            "G" to G, "G#" to G_SHARP, "Ab" to G_SHARP,
            "A" to A, "A#" to A_SHARP, "Bb" to A_SHARP, "B" to B
        )

        fun fromString(name: String): Note = stringMap[name] ?: error("Invalid note: $name")
        
        fun fromSemitone(semitone: Int): Note {
            val normalized = (semitone % 12 + 12) % 12
            return entries.first { it.semitone == normalized }
        }
    }

    fun display(useFlats: Boolean = false): String = when (this) {
        C -> "C"
        C_SHARP -> if (useFlats) "Db" else "C#"
        D -> "D"
        D_SHARP -> if (useFlats) "Eb" else "D#"
        E -> "E"
        F -> "F"
        F_SHARP -> if (useFlats) "Gb" else "F#"
        G -> "G"
        G_SHARP -> if (useFlats) "Ab" else "G#"
        A -> "A"
        A_SHARP -> if (useFlats) "Bb" else "A#"
        B -> "B"
    }
}
