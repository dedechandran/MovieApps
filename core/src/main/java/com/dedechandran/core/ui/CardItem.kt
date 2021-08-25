package com.dedechandran.core.ui

sealed class CardItem{
    data class Movie(
        val id: String,
        val urlImage: String
    ): CardItem()
}
