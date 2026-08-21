package com.example.musicrender.model.chordsallday

import com.example.musicrender.model.chordsallday.ChordADImage
import com.example.musicrender.model.chordsallday.ChordADType
import com.example.musicrender.model.chordsallday.LangString
import com.example.musicrender.model.chordsallday.NoteAD

data class ChordADEntry(
    val name: LangString,
    val images: ChordADImage,
    val _id: String,
    val noteADS: List<NoteAD>,
    val noteAD: NoteAD,
    val type: ChordADType
)