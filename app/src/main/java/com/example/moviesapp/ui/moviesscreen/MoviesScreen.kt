package com.example.moviesapp.ui.moviesscreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moviesapp.data.api.Movie
import com.example.moviesapp.ui.theme.MoviesAppTheme

@Composable
fun MoviesScreen(modifier: Modifier = Modifier, moviesViewModel: MoviesViewModel = hiltViewModel()) {
    val movies by moviesViewModel.uiState.collectAsStateWithLifecycle()

    if (movies is MoviesUiState.Success){
        MoviesList(movies = (movies as MoviesUiState.Success).data, modifier)
    }
}

@Composable
fun MoviesList(movies: List<Movie>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(movies) { movie ->
            MovieItem(movieName = movie.title)
        }
    }
}

@Composable
fun MovieItem(movieName: String) {
    Text(
        text = movieName,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoviesAppTheme {
        MoviesList(movies = listOf(Movie("title 1", "overview", "01/01/2023", "poster_path")))
    }
}
