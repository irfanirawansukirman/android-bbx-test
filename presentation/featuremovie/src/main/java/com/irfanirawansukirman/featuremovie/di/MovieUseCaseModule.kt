package com.irfanirawansukirman.featuremovie.di

import com.irfanirawansukirman.featuremovie.domain.repository.MovieRepository
import com.irfanirawansukirman.featuremovie.domain.usecase.MovieUseCase
import com.irfanirawansukirman.featuremovie.domain.usecase.MovieUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object MovieUseCaseModule {

    @Provides
    @ActivityRetainedScoped
    fun provideMovieUseCaseImpl(
        movieRepository: MovieRepository
    ): MovieUseCase = MovieUseCaseImpl(movieRepository)
}