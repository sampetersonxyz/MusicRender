package com.example.musicrender.model.chordsallday

data class ChordADEntry(
    val name: LangString,
    val images: ChordADImage,
    val _id: String,
    val noteADS: List<NoteAD>,
    val noteAD: NoteAD,
    val type: ChordADType
)
