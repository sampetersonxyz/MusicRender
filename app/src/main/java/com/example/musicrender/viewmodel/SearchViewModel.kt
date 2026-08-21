package com.example.musicrender.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicrender.model.Chord
import com.example.musicrender.model.ChordType
import com.example.musicrender.model.Note
import com.example.musicrender.model.chordsallday.ChordADEntry
import com.example.musicrender.model.chordsallday.ChordsListResponse
import com.example.musicrender.model.chordsallday.ChordsRetrofitInstance
import com.example.musicrender.model.GuitarChordGenerator
import com.example.musicrender.model.GuitarFingering
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.max

class SearchViewModel: ViewModel() {

    val cordsQueryList  = mutableStateOf<List<ChordADEntry>>(emptyList())

    var search: MutableState<String> = mutableStateOf<String>("")
        private set

    var searchedChord: MutableState<String> = mutableStateOf("")
        private set

    var rectX: MutableState<Int> = mutableStateOf(2)
        private set

    var chordList: MutableState<List<GuitarFingering>> = mutableStateOf(listOf())
        private set


    var chordRenderStartX: MutableState<Int> = mutableStateOf(RENDER_START_X)
    var chordRenderStartY: MutableState<Int> = mutableStateOf(RENDER_START_Y)

    val repository = ChordsRetrofitInstance.api

    fun onSearchChange(query: String){
        search.value = query
    }

    fun animateCanvas() {
        val root = Note.G
        val type = ChordType.MAJOR

        val CChord = Chord(root, type)
        val generator = GuitarChordGenerator()
        val fingerings = generator.generateOpenFingerings(CChord)
        searchedChord.value = root.toString() + " " +  type.toString()



        val threeOrMore = mutableListOf<GuitarFingering>()
        for (f in fingerings) {
            var playedStringCount = 0
            var playedNoteCount = 0
            var containsNull = false
            for (fret in f.frets) {
                if(fret != null) {
                    playedStringCount ++
                    if(fret != 0) {
                        playedNoteCount ++
                    }
                } else {
                    containsNull = true
                }
            }
            var isMutedEdge = true
            var isMiddleMute = false
            for (i in 0..2) {
                if(f.frets[i] != null)  {
                    isMutedEdge = false
                }
                if (!isMutedEdge && f.frets[i] == null) {
                    isMiddleMute = true
                }
            }
            isMutedEdge = true
            if(!isMiddleMute) {
                for (i in listOf(5,4,3)) {
                    if(f.frets[i] != null)  {
                        isMutedEdge = false
//                        Log.d("TEST", "i "  + i + " mutededge " + isMutedEdge)
                    }
                    if (!isMutedEdge && f.frets[i] == null) {
                        isMiddleMute = true
//                        Log.d("TEST", "i "  + i + " middle " + isMiddleMute)
                    }
                }
            }

            if(playedStringCount > 3 && playedNoteCount < 5 && !isMiddleMute) {
                threeOrMore.add(f)
            }
        }

        var maxStrings = 0
        for (chord in threeOrMore) {
            if(chord.playedStrings() > maxStrings) {
                maxStrings = chord.playedStrings()
            }
        }
        val maxList = mutableListOf<GuitarFingering>()
        for(chord in threeOrMore) {
            if(chord.playedStrings() == maxStrings) {
               maxList.add(chord)
            }
        }


        Log.d("TEST", listOf(
            "SHOW ME CHORD",
            CChord,
            CChord.notes,
            maxList,
        ).toString())

        chordList.value = maxList


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
            "g",
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
        const val RENDER_START_Y = 140
    }
}