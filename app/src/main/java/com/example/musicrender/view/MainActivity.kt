package com.example.musicrender.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicrender.model.GuitarFingering
import com.example.musicrender.ui.theme.MusicRenderTheme
import com.example.musicrender.viewmodel.SearchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicRenderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Search(
                        viewModel = viewModel<SearchViewModel>(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Search(viewModel: SearchViewModel, modifier: Modifier = Modifier) {
    val chordItems by remember { viewModel.chordList }
    LazyColumn(
        modifier
            .fillMaxSize()
            .background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        item {
            Box(modifier = Modifier.size(64.dp))
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                TextField(
                    value = viewModel.search.value,
                    onValueChange = { viewModel.onSearchChange(it) },
                    label = { Text(text = "Search") },
                )
                Button(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = {
                        viewModel.searchQuery()
                        viewModel.animateCanvas()
                    }
                ) {
                    Text("Search")
                }
            }
        }
        items(chordItems) { item ->
            // Calling the shared ChordRender component from commonMain
            ChordRender(
                chordName = viewModel.searchedChord.value,
                fingering = item
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 800)
@Composable
fun WebSearchPreview() {
    MusicRenderTheme {
        ChordRender("C Major", GuitarFingering(listOf(null, 3, 2, 0, 1, 0)))
    }
}
