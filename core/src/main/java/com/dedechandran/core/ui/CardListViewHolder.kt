package com.dedechandran.core.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dedechandran.core.R
import com.dedechandran.core.databinding.MovieItemBinding

class CardListViewHolder(
    view: View,
    private val itemClickListener: ((String) -> Unit)? = null
) : RecyclerView.ViewHolder(view) {

    fun bind(item: CardItem) {
        when (item) {
            is CardItem.Movie -> bindMovieItem(item)
        }
    }

    private fun bindMovieItem(item: CardItem.Movie) {
        val binding = MovieItemBinding.bind(itemView)
        with(binding) {
            Glide.with(itemView).load(item.urlImage)
                .into(ivMovie)
            tvTitle.text = item.title
            tvGenres.text = item.genres
            tvOverview.text = item.overview
            tvReleaseDate.text = item.releaseDate
            divContainer.setOnClickListener {
                itemClickListener?.invoke(item.id)
            }
        }
    }
}