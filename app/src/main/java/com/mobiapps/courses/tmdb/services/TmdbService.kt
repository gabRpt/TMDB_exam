package com.mobiapps.courses.tmdb.services

import android.content.Context
import com.mobiapps.courses.tmdb.CustomApplication
import com.mobiapps.courses.tmdb.datasources.locale.MovieDao
import com.mobiapps.courses.tmdb.datasources.remote.MockDataSource
import com.mobiapps.courses.tmdb.datasources.remote.NetworkDataSource
import com.mobiapps.courses.tmdb.datasources.remote.dtos.GenreDto
import com.mobiapps.courses.tmdb.entities.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TmdbService(context: Context, private val mock: Boolean = false) {
    private val mockDataSource = MockDataSource()
    private val networkDataSource = NetworkDataSource()
    private val movieDao: MovieDao

    init {
        val db = (context.applicationContext as CustomApplication).getDb()

        movieDao = db.movieDao()
    }

    fun getMovieDetail(id: Int, success: (movie: Movie?) -> Unit, failure: () -> Unit) {
        if (mock)
            success(mockDataSource.movies.find { it.id == id })
        else
            CoroutineScope(Dispatchers.IO).launch {
                networkDataSource.getMovieById(id, success = { movie ->
                    val movieDb = movieDao.getById(id)
                    movieDb?.let {
                        movie.favorite = true
                    }
                    success(movie)
                }, failure = {
                    failure()
                })
            }
    }

    fun getLatestMovies(success: (movies: List<Movie>) -> Unit, failure: () -> Unit) {
        if (mock)
            success(mockDataSource.movies)
        else {
            CoroutineScope(Dispatchers.IO).launch {
                networkDataSource.getLatestMovies(success = { movies ->
                    val moviesDb = movieDao.getAll()
                    movies.map { movie ->
                        movie.favorite = moviesDb.any { it.id == movie.id }
                    }
                    success(movies)
                }, failure = {
                    failure()
                })
            }
        }
    }

    fun getMoviesByName(name: String, success: (movies: List<Movie>) -> Unit, failure: () -> Unit) {
        if (mock)
            success(mockDataSource.movies)
        else {
            CoroutineScope(Dispatchers.IO).launch {
                networkDataSource.getMoviesByName(name, success = { movies ->
                    val moviesDb = movieDao.getAll()
                    movies.map { movie ->
                        movie.favorite = moviesDb.any { it.id == movie.id }
                    }
                    success(movies)
                }, failure = {
                    failure()
                })
            }
        }
    }

    fun toggleFavorite(movie: Movie, favorite: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            if (favorite)
                movieDao.addFavorite(movie)
            else
                movieDao.removeFavorite(movie)
        }
    }


    fun getMoviesByGenre(genre: Int, success: (movies: List<Movie>) -> Unit, failure: () -> Unit) {
        if (mock)
            success(mockDataSource.movies)
        else {
            CoroutineScope(Dispatchers.IO).launch {
                if (genre == 0) {
                    getLatestMovies(success, failure)
                } else {
                    networkDataSource.getMoviesByGenre(genre, success = { movies ->
                        val moviesDb = movieDao.getAll()
                        movies.map { movie ->
                            movie.favorite = moviesDb.any { it.id == movie.id }
                        }
                        success(movies)
                    }, failure = {
                        failure()
                    })
                }
            }
        }
    }

    fun getGenres(success: (genres: List<GenreDto>) -> Unit, failure: () -> Unit) {
        if (mock)
            success(mockDataSource.genres)
        else {
            CoroutineScope(Dispatchers.IO).launch {
                networkDataSource.getGenres(success = { genres ->
                    success(genres)
                }, failure = {
                    failure()
                })
            }
        }
    }
}