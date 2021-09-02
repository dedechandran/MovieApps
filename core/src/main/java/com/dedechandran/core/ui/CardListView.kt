package com.dedechandran.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dedechandran.core.R
import com.dedechandran.core.databinding.CardListViewBinding

class CardListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private val cardListAdapter by lazy {
        CardListViewAdapter()
    }

    init {
        super.setAdapter(cardListAdapter)
        super.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
    }


    fun setItems(items: List<CardItem>){
        cardListAdapter.submitList(items)
    }

    fun setOnItemClickListener(listener: (String) -> Unit){
        cardListAdapter.setOnItemClickListener(listener)
    }

    fun setOnFavoriteClickListener(listener: (String, Boolean) -> Unit){
        cardListAdapter.setOnFavoriteClickListener(listener)
    }

}