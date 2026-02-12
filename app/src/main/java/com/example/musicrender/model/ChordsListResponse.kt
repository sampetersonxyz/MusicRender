package com.example.musicrender.model

data class ChordsListResponse(
    val count: Int,
    val currentPage: Int,
    val totalPages: Int,
    val limit: Int,
    val data: List<ChordEntry>,
)
