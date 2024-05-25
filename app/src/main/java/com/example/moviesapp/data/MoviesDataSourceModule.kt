package com.example.moviesapp.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindMoviesDataSource(
        remoteMoviesDataSource: RemoteMoviesDataSource
    ): MoviesDataSource
}