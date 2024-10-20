package com.weijie.moview.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

class MoviePagingSource(
    private val api: MovieApi,
    private val query: String,
    private val movieDao: MovieDao
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response = api.searchMovies(query, page = page)
            val movies = response.Search

            // 缓存电影到数据库
            movieDao.insertMovies(movies.map { movie ->
                MovieEntity(
                    imdbID = movie.imdbID,
                    title = movie.Title,
                    year = movie.Year,
                    posterUrl = movie.Poster
                )
            })

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }
}