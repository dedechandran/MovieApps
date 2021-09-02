package com.dedechandran.movieapps.favorite.di

import android.content.Context
import com.dedechandran.movieapps.di.FavoriteDependencies
import com.dedechandran.movieapps.favorite.FavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteDependencies::class], modules = [ViewModelModule::class])
interface FavoriteComponent {

    fun inject(fragment: FavoriteFragment)

    @Component.Builder
    interface Builder{
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteDependencies: FavoriteDependencies): Builder
        fun build(): FavoriteComponent
    }


}