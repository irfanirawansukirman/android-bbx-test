package com.irfanirawansukirman.featuremovie.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object MovieCacheModule {

//    @Provides
//    @Singleton
//    fun provideMovieDao(cache: DbConfig): MovieDao {
//        return cache.movieDao()
//    }
}