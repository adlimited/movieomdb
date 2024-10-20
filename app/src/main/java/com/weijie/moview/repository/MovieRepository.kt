package com.weijie.moview.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.weijie.moview.data.Movie
import com.weijie.moview.data.MovieDao
import com.weijie.moview.data.MovieDatabasePagingSource
import com.weijie.moview.data.MovieDetail
import com.weijie.moview.data.MovieEntity
import com.weijie.moview.data.MoviePagingSource
import com.weijie.moview.data.RetrofitInstance
import com.weijie.moview.utility.NetworkChecker
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieDao: MovieDao,
    private val networkChecker: NetworkChecker
) {

    fun getMoviesPaging(query: String): Flow<PagingData<Movie>> {
        return if (networkChecker.isNetworkAvailable()) {
            Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = false),
                pagingSourceFactory = { MoviePagingSource(RetrofitInstance.api, query, movieDao) }
            ).flow
        } else {
            Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = false),
                pagingSourceFactory = { MovieDatabasePagingSource(movieDao) }
            ).flow
        }
    }

    suspend fun getMovieDetails(imdbID: String): MovieDetail {
        return RetrofitInstance.api.getMovieDetails(imdbID)
    }
}