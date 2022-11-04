package com.mobiapps.courses.tmdb.pages.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.mobiapps.courses.tmdb.R
import com.mobiapps.courses.tmdb.datasources.remote.dtos.GenreDto


// class to init all the genre in the spinner
// retrieve the genre from TmdbServie
class MovieSearchGenreSpinnerAdapter(
    context: Context,
    private val genres: List<GenreDto>
) : ArrayAdapter<GenreDto>(context, R.layout.movie_search_spinner_item, genres) {
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.movie_search_spinner_item, parent, false)

        val genre = genres[position]
        view.findViewById<TextView>(R.id.name).text = genre.name

        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.movie_search_spinner_item, parent, false)

        val genre = genres[position]
        view.findViewById<TextView>(R.id.name).text = genre.name

        return view
    }
}