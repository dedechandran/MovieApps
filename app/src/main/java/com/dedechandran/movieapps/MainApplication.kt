package com.dedechandran.movieapps

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this);
    }
}