package com.example.musicrender.model

data class GuitarFingering(
    val frets: List<Int?>
) {
    init {
        require(frets.size == 6) {
            "Guitar must have 6 Strings"
        }
    }

    fun highestFret(): Int {
        return frets.filterNotNull().maxOrNull() ?: 0
    }

    fun lowestFret(): Int {
        return frets.filterNotNull().minOrNull() ?: 0
    }

    fun isOpenChord(): Boolean {
        return frets.any {it == 0}
    }

    fun playedStrings(): Int {
        return frets.filterNotNull().count()
    }

    fun fingeredStrings(): Int {
        val noNull = frets.filterNotNull()
        var count = 0
        for (f in noNull ) {
           if(f != 0)  {
               count ++
           }
        }
        return count
    }

    override fun toString(): String {
        return frets.joinToString(separator = " ") {
            it?.toString() ?: "X"
        }
    }
}
