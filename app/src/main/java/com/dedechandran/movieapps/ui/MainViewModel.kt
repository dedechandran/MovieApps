package com.dedechandran.movieapps.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.dedechandran.movieapps.ConnectionLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    context: Context
): ViewModel(){

    val connectionState = ConnectionLiveData(context = context)

}