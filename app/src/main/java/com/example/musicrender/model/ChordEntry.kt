package com.example.musicrender.model

data class ChordEntry(
    val name: LangString,
    val images: ChordImage,
    val _id: String,
    val notes: List<Note>,
    val note: Note,
    val type: ChordType
)
