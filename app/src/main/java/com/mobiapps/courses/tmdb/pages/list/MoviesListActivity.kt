package com.mobiapps.courses.tmdb.pages.list

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobiapps.courses.tmdb.pages.search.MovieSearchActivity
import com.mobiapps.courses.tmdb.R
import com.mobiapps.courses.tmdb.entities.Movie
import com.mobiapps.courses.tmdb.pages.detail.MovieDetailActivity
import com.mobiapps.courses.tmdb.services.TmdbService


class MoviesListActivity : AppCompatActivity() {
    private lateinit var tmdbService: TmdbService
    private lateinit var moviesListAdapter: MoviesListAdapter
    private var moviesList: List<Movie>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)
        tmdbService = TmdbService(this)

        moviesList = savedInstanceState?.getParcelableArrayList("MOVIES_LIST")
    }

    override fun onStart() {
        super.onStart()

        val moviesListView = findViewById<RecyclerView>(R.id.moviesList)

        moviesListAdapter = MoviesListAdapter({
            navigateToDetail(it)
        }, { movie, favorite ->
            tmdbService.toggleFavorite(movie, favorite)
        })

        moviesListView.adapter = moviesListAdapter
        moviesListView.layoutManager = GridLayoutManager(this, 3)

        if (moviesList == null) {
            tmdbService.getLatestMovies(success = {
                runOnUiThread {
                    moviesListAdapter.dataSet = it
                }
            }, failure = {})
        } else {
            moviesListAdapter.dataSet = moviesList as List<Movie>
        }

        // move to MovieSearchActivity when clicking on searchButton
        val searchButton = findViewById<ImageButton>(R.id.searchButton)
        searchButton.setOnClickListener {
            val intent = Intent(this, MovieSearchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("MOVIES_LIST", ArrayList(moviesListAdapter.dataSet))
        super.onSaveInstanceState(outState)
    }

    private fun navigateToDetail(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.INTENT_PARAM_ID, movie.id)
        startActivity(intent)
    }
}