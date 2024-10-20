package com.weijie.moview.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.weijie.moview.data.Movie
import com.weijie.moview.data.MovieDetail
import com.weijie.moview.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail: StateFlow<MovieDetail?> = _movieDetail

    fun getMovieDetails(imdbID: String): StateFlow<MovieDetail?> {
        viewModelScope.launch {
            try {
                val detail = repository.getMovieDetails(imdbID)
                _movieDetail.value = detail
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching movie details: ${e.message}")
            }
        }
        return _movieDetail
    }

    fun getMoviesPaging(query: String = "Marvel"): Flow<PagingData<Movie>> {
        Log.d("MovieViewModel", "Fetching movies with query: $query")

        // 从仓库获取分页数据并缓存
        return repository.getMoviesPaging(query).cachedIn(viewModelScope)
    }

}