package com.example.moviesapp.data.di

import com.example.moviesapp.data.api.MovieResponseMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideMovieResponseMapper(): MovieResponseMapper {
        return MovieResponseMapper()
    }
}