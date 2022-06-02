package com.patrickmota.moviedatabase.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.patrickmota.moviedatabase.R
import com.patrickmota.moviedatabase.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val onItemClicked : (Movie) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: List<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                holder.bind(items[position], onItemClicked)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setDataSet(movies: List<Movie>){
        this.items = movies
    }

    class MovieViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private lateinit var movie: Movie

        fun bind(movie: Movie, onItemClicked: (Movie) -> Unit) {
            this.movie = movie

            itemView.title.text = movie.title
            itemView.date.text = movie.releaseDate
            itemView.genre.text = movie.genres?.get(0)?.name
            itemView.popularity.text = movie.popularity.toString()

            val bannerUrl = "https://image.tmdb.org/t/p/w500/" + movie.posterPath

            val requestOptions = RequestOptions()
                .placeholder(R.color.gray)
                .error(R.color.gray)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(bannerUrl)
                .fitCenter()
                .into(itemView.image)

            itemView.setOnClickListener{
                onItemClicked(movie)
            }
        }
    }

}