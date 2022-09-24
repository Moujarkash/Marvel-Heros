package com.mod.marvelcomic.di

import com.mod.marvelcomic.domain.repositories.ComicCharacterRepository
import com.mod.marvelcomic.infrastructure.core.AppDatabase
import com.mod.marvelcomic.infrastructure.datasources.ComicCharacterRemoteDataSource
import com.mod.marvelcomic.infrastructure.repositories.ComicCharacterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideComicCharacterRepository(
        comicCharacterRemoteDataSource: ComicCharacterRemoteDataSource,
        database: AppDatabase
    ): ComicCharacterRepository {
        return ComicCharacterRepositoryImpl(comicCharacterRemoteDataSource, database)
    }
}