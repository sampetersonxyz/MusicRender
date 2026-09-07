package com.example.musicrender.model.chordsallday

data class ChordsListResponse(
    val count: Int,
    val currentPage: Int,
    val totalPages: Int,
    val limit: Int,
    val data: List<ChordADEntry>,
)
