package com.mobiapps.courses.tmdb.datasources.remote

import com.mobiapps.courses.tmdb.datasources.remote.dtos.GenresListDto
import com.mobiapps.courses.tmdb.datasources.remote.dtos.MovieDto
import com.mobiapps.courses.tmdb.datasources.remote.dtos.MoviesListDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbService {
    @GET("movie/now_playing")
    suspend fun getLatestMovies(): Response<MoviesListDto>

    @GET("movie/{id}")
    suspend fun getMovieById(@Path("id") id: Int): Response<MovieDto>

    @GET("search/movie")
    suspend fun getMovieByName(@Query("query") query: String): Response<MoviesListDto>

    @GET("genre/{id}/movies")
    suspend fun getMoviesByGenre(@Path("id") id: Int): Response<MoviesListDto>

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenresListDto>
}