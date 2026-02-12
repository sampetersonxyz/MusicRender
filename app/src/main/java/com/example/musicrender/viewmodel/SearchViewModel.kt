package com.example.musicrender.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicrender.model.ChordEntry
import com.example.musicrender.model.ChordsListResponse
import com.example.musicrender.model.ChordsRetrofitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {

    val cordsQueryList  = mutableStateOf<List<ChordEntry>>(emptyList())

    var search: MutableState<String> = mutableStateOf<String>("")
        private set

    var rectX: MutableState<Int> = mutableStateOf(2)
        private set


    var chordRenderStartX: MutableState<Int> = mutableStateOf(RENDER_START_X)
    var chordRenderStartY: MutableState<Int> = mutableStateOf(RENDER_START_Y)

    val repository = ChordsRetrofitInstance.api

    fun onSearchChange(query: String){
        search.value = query
    }

    fun animateCanvas() {
        viewModelScope.launch {
            while (true) {
                rectX.value = rectX.value + 1
                if(rectX.value > 10) {
                    rectX.value = 0
                }
                delay(100)
            }
        }
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


    companion object {
        const val RENDER_START_X = 100
        const val RENDER_START_Y = 50
    }
}