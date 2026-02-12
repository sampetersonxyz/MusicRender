package com.example.musicrender.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ChordsApiService {
    // Add your API endpoints here

    @GET("v1/chords")
    fun getChords(
        @Query ("limit") limit: String,
        @Query ("page") page: String,
        @Query("note") note: String,
        @Query ("type") type: String
    ): Call<ChordsListResponse>
}