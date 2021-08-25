package com.dedechandran.core.di

import com.dedechandran.core.data.local.ILocalDataSource
import com.dedechandran.core.data.local.LocalDataSource
import com.dedechandran.core.data.remote.IRemoteDataSource
import com.dedechandran.core.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideLocalDataSource(localDataSource: LocalDataSource): ILocalDataSource {
        return localDataSource
    }

    @Provides
    fun provideRemoteDataSource(remoteDataSource: RemoteDataSource): IRemoteDataSource {
        return remoteDataSource
    }

}