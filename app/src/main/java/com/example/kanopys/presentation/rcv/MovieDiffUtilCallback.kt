package com.example.kanopys.presentation.rcv

import androidx.recyclerview.widget.DiffUtil
import com.example.kanopys.domain.entity.Movie

class MovieDiffUtilCallback: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}