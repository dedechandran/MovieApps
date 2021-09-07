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
            item.urlImage?.let {
                Glide.with(itemView).load(item.urlImage)
                    .into(ivMovie)
            } ?: ivMovie.setImageResource(R.drawable.ic_baseline_broken_image_24)

            tvTitle.text = item.title ?: NO_DATA
            tvGenres.text = item.genres ?: NO_DATA
            tvOverview.text = item.overview ?: NO_DATA
            tvReleaseDate.text = item.releaseDate ?: NO_DATA
            divContainer.setOnClickListener {
                itemClickListener?.invoke(item.id)
            }
        }
    }

    companion object {
        private const val NO_DATA = "-"
    }
}