package com.weijie.moview.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.weijie.moview.viewmodel.MovieViewModel

@Composable
fun MovieDetailScreen(imdbID: String, navController: NavHostController, viewModel: MovieViewModel = hiltViewModel()) {
    val movieDetail by viewModel.getMovieDetails(imdbID).collectAsState()

    movieDetail?.let { movie ->
        Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = { navController.popBackStack() }) {
                Text("Back to List")
            }

            Text("Title: ${movie.Title}")
            Text("Year: ${movie.Year}")
            Text("Plot: ${movie.Plot}")
            Image(
                painter = rememberImagePainter(movie.Poster),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            )
        }
    }
}