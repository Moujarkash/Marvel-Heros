package com.mod.marvelcomic.di

import com.mod.marvelcomic.infrastructure.apiservices.ComicCharacterApiService
import com.mod.marvelcomic.infrastructure.datasources.ComicCharacterRemoteDataSource
import com.mod.marvelcomic.infrastructure.datasources.ComicCharacterRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {
    @Provides
    @Singleton
    fun provideComicCharacterRemoteDataSource(comicCharacterApiService: ComicCharacterApiService): ComicCharacterRemoteDataSource {
        return ComicCharacterRemoteDataSourceImpl(comicCharacterApiService)
    }
}