package com.irfanirawansukirman.featuremovie.di

import com.irfanirawansukirman.featuremovie.data.service.MovieService
import com.irfanirawansukirman.featuremovie.domain.repository.MovieRepository
import com.irfanirawansukirman.featuremovie.data.repository.MovieRepositoryImpl
import com.irfanirawansukirman.libraryutil.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object MovieRepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideMovieRepositoryImpl(
        coroutineContextProvider: CoroutineContextProvider,
        movieService: MovieService
    ): MovieRepository = MovieRepositoryImpl(coroutineContextProvider, movieService)
}