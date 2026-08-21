package com.example.musicrender.model

enum class ChordType (
    val symbol: String,
    val intervals: List<Interval>
){
    MAJOR(
        "",
        listOf(
            Interval.ROOT,
            Interval.MAJOR_THIRD,
            Interval.PERFECT_FIFTH
        )
    ),
    MINOR(
    "m",
    listOf(
            Interval.ROOT,
            Interval.MINOR_THIRD,
            Interval.PERFECT_FIFTH
        )
    ),
    DOMINANT_7(
        "7",
        listOf(
            Interval.ROOT,
            Interval.MAJOR_THIRD,
            Interval.PERFECT_FIFTH,
            Interval.MINOR_SEVENTH
        )
    ),
    MAJOR_7(
        "maj7",
        listOf(
            Interval.ROOT,
            Interval.MAJOR_THIRD,
            Interval.PERFECT_FIFTH,
            Interval.MAJOR_SEVENTH
        )
    );

    override fun toString(): String {
        return when (this) {
            ChordType.MAJOR -> ""
            ChordType.MINOR -> "Minor"
            ChordType.DOMINANT_7 -> "7"
            ChordType.MAJOR_7 -> "Maj7"
        }
    }
}