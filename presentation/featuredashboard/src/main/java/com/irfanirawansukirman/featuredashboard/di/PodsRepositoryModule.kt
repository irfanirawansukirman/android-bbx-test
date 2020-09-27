package com.irfanirawansukirman.featuredashboard.di

import android.content.Context
import com.irfanirawansukirman.featuredashboard.data.repository.PodsRepositoryImpl
import com.irfanirawansukirman.featuredashboard.domain.repository.PodsRepository
import com.irfanirawansukirman.libraryutil.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object PodsRepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun providePodsRepositoryImpl(
        @ApplicationContext appContext: Context,
        coroutineContextProvider: CoroutineContextProvider,
    ): PodsRepository = PodsRepositoryImpl(appContext, coroutineContextProvider)
}