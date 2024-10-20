package com.weijie.moview.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("?apikey=6fc87060")
    suspend fun searchMovies(
        @Query("s") query: String,
        @Query("type") type: String? = "movie",
        @Query("page") page: Int = 1
    ): SearchResponse


    @GET("?apikey=6fc87060")
    suspend fun getMovieDetails(
        @Query("i") imdbID: String
    ): MovieDetail
}

data class SearchResponse(val Search: List<Movie>)
data class Movie(val Title: String, val Year: String, val imdbID: String, val Poster: String)
data class MovieDetail(val Title: String, val Year: String, val Plot: String, val Poster: String)

object RetrofitInstance {
    private const val BASE_URL = "https://www.omdbapi.com/"

    val api: MovieApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }
}
