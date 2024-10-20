package com.weijie.moview.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.weijie.moview.data.Movie
import com.weijie.moview.viewmodel.MovieViewModel

@Composable
fun MovieListScreen(navController: NavHostController, viewModel: MovieViewModel = hiltViewModel()) {
    val listState = rememberLazyListState()

    var query by remember { mutableStateOf("") }
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(searchText) {
        kotlinx.coroutines.delay(500)
        query = searchText
    }

    val movies = viewModel.getMoviesPaging(if (query.isEmpty()) "Marvel" else query).collectAsLazyPagingItems()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search Movies") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            items(movies) { movie ->
                if (movie != null) {
                    MovieItem(movie) {
                        navController.navigate("movieDetail/${movie.imdbID}")
                    }
                }
            }
        }
    }
}


@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = rememberImagePainter(movie.Poster),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(movie.Title)
            Text(movie.Year)
        }
    }
}