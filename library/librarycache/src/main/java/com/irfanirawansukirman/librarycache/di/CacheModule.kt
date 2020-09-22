package com.irfanirawansukirman.librarycache.di

import android.app.Application
import androidx.room.Room
import com.irfanirawansukirman.librarycache.DbConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideDbConfig(application: Application): DbConfig {
        return Room.databaseBuilder(
            application,
            DbConfig::class.java,
            "db_movie"
        )
            .build()
    }
}