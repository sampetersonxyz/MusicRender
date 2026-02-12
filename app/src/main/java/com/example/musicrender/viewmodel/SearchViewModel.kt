package com.example.musicrender.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.musicrender.model.ChordEntry
import com.example.musicrender.model.ChordsListResponse
import com.example.musicrender.model.ChordsRetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {

    val cordsQueryList  = mutableStateOf<List<ChordEntry>>(emptyList())

    var search: MutableState<String> = mutableStateOf<String>("")
        private set

    val repository = ChordsRetrofitInstance.api

    fun onSearchChange(query: String){
        search.value = query
    }

    fun searchQuery() {
        Log.d("TEST", "reached searchQuery")
        repository.getChords(
            "1",
            "1",
            "c",
            "major"
        ).enqueue(object : Callback<ChordsListResponse> {
            override fun onResponse(
                call: Call<ChordsListResponse>,
                response: Response<ChordsListResponse>
            ) {

                Log.d("TEST","reached response")
                if (response.isSuccessful) {
                    val list = response.body()?.let {
                        cordsQueryList.value = it.data
                        Log.d("TEST",listOf(
                            "rawList",
                            it.data
                        ).toString())
                    }
                } else {
                    Log.d("TEST", listOf(
                        "response",
                        response
                    ).toString())
                }
            }

            override fun onFailure(
                call: Call<ChordsListResponse>,
                t: Throwable
            ) {
                Log.e("SearchViewModel", "API call failed", t)
            }
        })
    }
}