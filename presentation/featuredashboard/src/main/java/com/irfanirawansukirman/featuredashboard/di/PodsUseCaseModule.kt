package com.irfanirawansukirman.featuredashboard.di

import com.irfanirawansukirman.featuredashboard.domain.repository.PodsRepository
import com.irfanirawansukirman.featuredashboard.domain.usecase.PodsUseCase
import com.irfanirawansukirman.featuredashboard.domain.usecase.PodsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object PodsUseCaseModule {

    @Provides
    @ActivityRetainedScoped
    fun providePodsUseCaseImpl(
        podsRepository: PodsRepository
    ): PodsUseCase = PodsUseCaseImpl(podsRepository)
}