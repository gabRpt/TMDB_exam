package com.mobiapps.courses.tmdb.pages.search

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mobiapps.courses.tmdb.R
import com.mobiapps.courses.tmdb.entities.Movie

class MoviesSearchAdapter(
    private val onClick: (Movie) -> Unit,
    private val onFavoriteToggle: (Movie, Boolean) -> Unit
) :
    RecyclerView.Adapter<MoviesSearchAdapter.ViewHolder>() {

    var dataSet: List<Movie> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val poster: ImageView
        val title: TextView
        val releaseDate: TextView
        val favorite: ToggleButton
        val vote: TextView

        init {
            with(view) {
                poster = findViewById(R.id.backdrop)
                title = findViewById(R.id.title)
                releaseDate = findViewById(R.id.releaseDate)
                favorite = findViewById(R.id.favorite)
                vote = findViewById(R.id.movieSearchVote)
                setOnClickListener {
                    onClick(dataSet[adapterPosition])
                }

                favorite.setOnCheckedChangeListener(object : View.OnClickListener,
                    CompoundButton.OnCheckedChangeListener {
                    override fun onClick(p0: View?) {
                        Log.d("setOnCheckedChangeListener", "onClick")
                    }

                    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                        Log.d("setOnCheckedChangeListener", "onCheckedChanged $p1")
                        onFavoriteToggle(dataSet[adapterPosition], p1)
                    }
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_search_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.poster.load("https://image.tmdb.org/t/p/original/${dataSet[position].posterUrl}")
        holder.title.text = dataSet[position].title
        holder.releaseDate.text = dataSet[position].releaseDate.toString()
        holder.favorite.isChecked = dataSet[position].favorite
        holder.vote.text = dataSet[position].averageVote.toString() + "/10" + " (" + dataSet[position].votesNumber.toString() + ")"
    }

    override fun getItemCount(): Int =
        dataSet.size

}