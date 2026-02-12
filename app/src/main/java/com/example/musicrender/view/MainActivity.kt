package com.example.musicrender.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicrender.R
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

val colorMatrix = floatArrayOf(
    -1f, 0f, 0f, 0f, 255f,
    0f, -1f, 0f, 0f, 255f,
    0f, 0f, -1f, 0f, 255f,
    0f, 0f, 0f, 1f, 0f
)

@Composable
fun Search(viewModel: SearchViewModel, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier
            .fillMaxSize()
            .background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        item{
            Box(
                modifier = Modifier.size(64.dp)
            )
        }
        item {
            Row() {
                TextField(
                    value = viewModel.search.value,
                    onValueChange = { viewModel.onSearchChange(it) },
                    label = {
                        Text(text = "Search")
                    },
                )
                Button(
                    onClick = {
                        viewModel.searchQuery()
                    }
                ) {
                    Text(
                        "Search",
                    )
                }
            }
        }
        item {
            Canvas(
                modifier = Modifier.fillMaxWidth()
                    .height(200.dp)
                    .background(color = Color.DarkGray)
            ) {
                val rectSize = 20.dp.toPx()
                drawRect(
                    color = Color.Cyan,
                    topLeft = Offset(10f, 10f),
                    size = Size(rectSize, rectSize)
                )
            }
        }
        items(5, key = { it + 1 }) {
            HorizontalDivider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(8.dp)

            )
            Text(
                text = "C7",
                color = Color.Cyan,
                fontSize = 40.sp
            )
            Image(
                painter = painterResource(id = R.drawable.chord_c7_pos1),
                contentDescription = "C7",
                colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    MusicRenderTheme {
        Search(viewModel<SearchViewModel>())
    }
}
