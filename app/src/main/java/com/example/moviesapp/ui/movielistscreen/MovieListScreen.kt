package com.example.moviesapp.ui.movielistscreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.moviesapp.data.api.Api
import com.example.moviesapp.data.source.models.Movie

@Composable
fun MovieListScreen(modifier: Modifier = Modifier, navController: NavController, moviesViewModel: MoviesViewModel = hiltViewModel()) {
    val uiState by moviesViewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.error != null) {
        Toast.makeText(navController.context, uiState.error, Toast.LENGTH_SHORT).show()
    }
    MoviesGrid(movies = uiState.data, moviesViewModel, uiState, modifier, navController = navController, onEndOfListReached = {moviesViewModel.fetchMovies(null)})

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviesGrid(movies: List<Movie>, moviesViewModel: MoviesViewModel, uiState: MoviesUiState, modifier: Modifier = Modifier, navController: NavController, onEndOfListReached: () -> Unit) {
    val gridState = rememberLazyGridState()
    val pullRefreshState = rememberPullRefreshState(uiState.isRefreshing, { moviesViewModel.refresh() })

    val reachedBottom: Boolean by remember {
        derivedStateOf {
            gridState.reachedBottom()
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) onEndOfListReached()
    }

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(2),
        modifier = modifier.pullRefresh(pullRefreshState)
    ) {
        items(movies) { movie ->
            MovieCard(movie = movie, navController = navController)
        }
    }
    PullRefreshIndicator(
        refreshing = uiState.isRefreshing,
        state = pullRefreshState
    )

}

internal fun LazyGridState.reachedBottom(buffer: Int = 1): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem?.index != 0 && lastVisibleItem?.index == this.layoutInfo.totalItemsCount - buffer
}

@Composable
fun MovieCard(movie: Movie, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { navController.navigate("movieDetail/${movie.id}") },
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = movie.posterPath.let {
                        Api.getPosterPath(
                            it
                        )
                    })
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                contentDescription = "Movie Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(Color.Black.copy(alpha = 0.6f))
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                movie.releaseDate?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
            }
        }
    }
}
