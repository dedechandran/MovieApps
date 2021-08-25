package com.dedechandran.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dedechandran.core.R

class CardListViewAdapter : ListAdapter<CardItem, CardListViewHolder>(DIFF_CALLBACK) {
    private var setOnItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListViewHolder {
        val layout = when(viewType){
            MOVIE_ITEM_TYPE -> R.layout.movie_item
            else -> R.layout.movie_item
        }
        val view = LayoutInflater.from(parent.context).inflate(layout,parent,false)
        return CardListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardListViewHolder, position: Int) {
        holder.bind(item = getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is CardItem.Movie -> MOVIE_ITEM_TYPE
        }
    }

    fun setOnItemClickListener(listener: (String) -> Unit){
        setOnItemClickListener = listener
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CardItem>(){
            override fun areItemsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
                return false
            }

        }

        private const val MOVIE_ITEM_TYPE = 0
    }
}