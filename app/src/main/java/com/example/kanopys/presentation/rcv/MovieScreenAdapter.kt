package com.example.kanopys.presentation.rcv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.kanopys.R
import com.example.kanopys.databinding.FilmItemBinding
import com.example.kanopys.domain.entity.Movie

class MovieScreenAdapter: ListAdapter<Movie, MovieScreenAdapter.MovieViewHolder>(MovieDiffUtilCallback()) {

    class MovieViewHolder(item: View): ViewHolder(item) {
        val binding = FilmItemBinding.bind(item)
        fun setData(movie: Movie) {
            binding.apply {
                titleMovie.text = movie.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.film_item,
            parent,
            false
        )
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.setData(item)
    }
}