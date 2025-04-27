package com.example.test.Ui.Movie.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.Ui.Movie.Model.MovieItem
import com.example.test.Ui.Movie.MoviesDetails

class MoviePagingAdapter :
    PagingDataAdapter<MovieItem, MoviePagingAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_single_pagging_xml, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitle: TextView = itemView.findViewById(R.id.movieTitle)
        private val releaseDate: TextView = itemView.findViewById(R.id.movieReleaseDate)
        private val movieDisplay: ConstraintLayout =
            itemView.findViewById<ConstraintLayout>(R.id.movieDisplay)

        fun bind(item: MovieItem) {
            textViewTitle.text = item.title
            releaseDate.text = item.release_date
            movieDisplay.setOnClickListener {
                val intent = Intent(itemView.context, MoviesDetails::class.java)
                intent.putExtra("title", item.title)
                intent.putExtra("discription", item.overview)
                itemView.context.startActivity(intent)
            }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieItem>() {
        override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem) = oldItem == newItem
    }
}