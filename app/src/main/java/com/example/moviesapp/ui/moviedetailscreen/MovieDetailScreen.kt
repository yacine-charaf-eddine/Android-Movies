package com.example.moviesapp.ui.moviedetailscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.moviesapp.data.api.Movie
import com.example.moviesapp.ui.movielistscreen.MoviesViewModel

@Composable
fun MovieDetailScreen(
    movieId: String,
    navController: NavController,
    moviesViewModel: MoviesViewModel = hiltViewModel()
) {
    moviesViewModel.fetchMovie(movieId)
    val movie by moviesViewModel.movie.collectAsStateWithLifecycle()

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        movie?.let {
            MoviePoster(posterPath = it.posterPath, onBack = { navController.navigateUp() })
        }
        movie?.let { MovieInfoRow(movie = it) }
        movie?.let { MovieOverview(overview = it.overview) }
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
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White  // Use a suitable color for visibility
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
                style = MaterialTheme.typography.labelLarge,
                color = Color.White.copy(alpha = 0.7F)
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
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
            }
            Text(
                text = "(${movie.voteCount})",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White.copy(alpha = 0.7F)
            )
        }
    }
}

@Composable
fun MovieOverview(overview: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Overview",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = overview,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.7F)
        )
    }
}