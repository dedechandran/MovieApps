package com.dedechandran.movieapps.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

class FavoriteMovieViewModel @Inject constructor(
) : ViewModel() {

    val text = MutableLiveData("Hellowwwwww")
}