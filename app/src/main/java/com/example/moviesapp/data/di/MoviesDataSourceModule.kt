package com.example.moviesapp.data.di

import com.example.moviesapp.data.source.MoviesDataSource
import com.example.moviesapp.data.source.RemoteMoviesDataSource
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