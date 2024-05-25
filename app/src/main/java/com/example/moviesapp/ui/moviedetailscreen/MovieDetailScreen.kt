package com.example.moviesapp.ui.moviedetailscreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.moviesapp.R
import com.example.moviesapp.data.api.Api
import com.example.moviesapp.data.source.models.Genre
import com.example.moviesapp.data.source.models.Movie

@Composable
fun MovieDetailScreen(
    movieId: String,
    navController: NavController,
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel()
) {
    movieDetailViewModel.fetchMovie(movieId)
    val uiState by movieDetailViewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.error != null){
        Toast.makeText(navController.context, uiState.error, Toast.LENGTH_SHORT).show()
    }
    if (uiState.data != null) {
        uiState.data?.let {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                MoviePoster(posterPath = it.posterPath, onBack = { navController.navigateUp() })
                MovieInfoRow(movie = it)
                MovieGenres(genres = it.genres)
                MovieOverview(overview = it.overview)
            }
        }
    }
}

@Composable
fun MoviePoster(posterPath: String, onBack: () -> Unit) {
    Box {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Api.getPosterPath(posterPath))
                    .apply {
                        crossfade(true)
                    }.build()
            ),
            contentDescription = "Movie Poster",
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }
}


@SuppressLint("DefaultLocale")
@Composable
fun MovieInfoRow(movie: Movie) {
    Row(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {

        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = movie.releaseDate,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = Modifier.weight(1F))

        Column(horizontalAlignment = Alignment.End) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = "Rating",
                    tint = Color.Yellow
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = String.format("%.1f", movie.voteAverage),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Text(
                text = "(${movie.voteCount})",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun MovieGenres(genres: List<Genre>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Genres",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(genres) { genre ->
                Chip(genre = genre.name)
            }
        }
    }
}

@Composable
fun Chip(genre: String) {
    Box(
        modifier = Modifier
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = genre,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}

@Composable
fun MovieOverview(overview: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Overview",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = overview,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}