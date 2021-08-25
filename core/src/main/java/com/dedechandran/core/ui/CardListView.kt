package com.dedechandran.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.dedechandran.core.R
import com.dedechandran.core.databinding.CardListViewBinding

class CardListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyle, defStyleRes) {

    private val cardListAdapter by lazy {
        CardListViewAdapter()
    }

    private var binding: CardListViewBinding

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = CardListViewBinding.inflate(layoutInflater)
        addView(binding.root)
    }

    init {
        binding.rvCardView.apply {
            adapter = cardListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }


    fun setItems(items: List<CardItem>){
        cardListAdapter.submitList(items)
    }

    fun setOnItemClickListener(listener: (String) -> Unit){
        cardListAdapter.setOnItemClickListener(listener)
    }

}