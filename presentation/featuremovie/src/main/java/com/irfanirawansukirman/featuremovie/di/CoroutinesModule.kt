package com.irfanirawansukirman.featuremovie.di

import com.irfanirawansukirman.libraryutil.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object CoroutinesModule {

    @Provides
    @Singleton
    fun provideCoroutineContextProvider(): CoroutineContextProvider = CoroutineContextProvider()
}