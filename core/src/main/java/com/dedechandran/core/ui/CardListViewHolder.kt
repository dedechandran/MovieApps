package com.dedechandran.core.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dedechandran.core.R
import com.dedechandran.core.databinding.MovieItemBinding

class CardListViewHolder(
    view: View,
    private val favoriteIconListener: ((String) -> Unit)? = null,
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
            val favoriteIcon = if (item.isFavorite) {
                R.drawable.ic_baseline_favorite_24
            } else {
                R.drawable.ic_baseline_favorite_border_24
            }
            ivMovieFavorite.setImageResource(favoriteIcon)
            ivMovieFavorite.setOnClickListener {
                favoriteIconListener?.invoke(item.id)
            }
            divContainer.setOnClickListener {
                itemClickListener?.invoke(item.id)
            }
        }
    }
}