package com.dedechandran.core.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dedechandran.core.databinding.MovieItemBinding

class CardListViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bind(item: CardItem){
        when(item){
            is CardItem.Movie -> bindMovieItem(item)
        }
    }

    private fun bindMovieItem(item: CardItem.Movie){
        val binding = MovieItemBinding.bind(itemView)
        with(binding){
            Glide.with(itemView).load("http://image.tmdb.org/t/p/w185${item.urlImage}").into(ivMovie)
        }
    }
}