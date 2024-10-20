package com.weijie.moview.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

class MovieDatabasePagingSource(
    private val movieDao: MovieDao
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            // 从数据库获取 MovieEntity，并通过页数来分页显示
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize
            val offset = (currentPage - 1) * pageSize

            // 从数据库中分页获取数据
            val data = movieDao.getMoviesPaged(offset, pageSize).map { entity ->
                Movie(
                    Title = entity.title,
                    Year = entity.year,
                    imdbID = entity.imdbID,
                    Poster = entity.posterUrl
                )
            }

            // 检查是否有更多页
            val nextPage = if (data.size < pageSize) null else currentPage + 1

            LoadResult.Page(
                data = data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        // 返回上次加载的键，这样在离线时可以从同样的页数开始
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}