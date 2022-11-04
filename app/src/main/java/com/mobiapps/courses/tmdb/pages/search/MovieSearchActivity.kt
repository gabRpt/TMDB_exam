package com.mobiapps.courses.tmdb.pages.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobiapps.courses.tmdb.R
import com.mobiapps.courses.tmdb.datasources.remote.dtos.GenreDto
import com.mobiapps.courses.tmdb.entities.Movie
import com.mobiapps.courses.tmdb.pages.detail.MovieDetailActivity
import com.mobiapps.courses.tmdb.services.TmdbService

class MovieSearchActivity : AppCompatActivity() {
    private lateinit var tmdbService: TmdbService
    private lateinit var movieSearchAdapter: MoviesSearchAdapter
    private var moviesList: List<Movie>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)
        tmdbService = TmdbService(this)

        moviesList = savedInstanceState?.getParcelableArrayList("MOVIES_LIST")
    }

    override fun onStart() {
        super.onStart()

        val moviesListView = findViewById<RecyclerView>(R.id.movieSearchList)

        movieSearchAdapter = MoviesSearchAdapter({
            navigateToDetail(it)
        }, { movie, favorite ->
            tmdbService.toggleFavorite(movie, favorite)
        })

        moviesListView.adapter = movieSearchAdapter
        moviesListView.layoutManager = LinearLayoutManager(this)

        if (moviesList == null) {
            tmdbService.getLatestMovies(success = {
                runOnUiThread {
                    movieSearchAdapter.dataSet = it
                }
            }, failure = {})
        } else {
            movieSearchAdapter.dataSet = moviesList as List<Movie>
        }

        val searchMovie = findViewById<EditText>(R.id.movieSearchBar)
        searchMovie.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                Log.d("searchMovie", "$s")
                tmdbService.getMoviesByName(s.toString(), success = {
                    runOnUiThread {
                        movieSearchAdapter.dataSet = it
                    }
                }, failure = {})
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })


        // when movieSearchActionGenreCheckBox is checked, only show movies with that type
        val movieSearchActionGenreCheckBox = findViewById<CheckBox>(R.id.movieSearchActionGenreCheckBox)
        movieSearchActionGenreCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                tmdbService.getMoviesByGenre(28, success = {
                    runOnUiThread {
                        movieSearchAdapter.dataSet = it
                    }
                }, failure = {})
            } else {
                tmdbService.getLatestMovies(success = {
                    runOnUiThread {
                        movieSearchAdapter.dataSet = it
                    }
                }, failure = {})
            }
        }


        val movieSearchGenreSpinner = findViewById<Spinner>(R.id.movieSearchGenreSpinner)
        tmdbService.getGenres(success = {
            runOnUiThread {
                val adapter = MovieSearchGenreSpinnerAdapter(this, it)
                movieSearchGenreSpinner.adapter = adapter
            }
        }, failure = {})


        movieSearchGenreSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val genre = parent.getItemAtPosition(position) as GenreDto
                tmdbService.getMoviesByGenre(genre.id, success = {
                    runOnUiThread {
                        movieSearchAdapter.dataSet = it
                    }
                }, failure = {})
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("MOVIES_LIST", ArrayList(movieSearchAdapter.dataSet))
        super.onSaveInstanceState(outState)
    }

    private fun navigateToDetail(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.INTENT_PARAM_ID, movie.id)
        startActivity(intent)
    }
}