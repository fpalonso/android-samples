package dev.ferp.navigationsamples.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ferp.navigationsamples.data.InMemoryPictureRepository
import dev.ferp.navigationsamples.data.PictureRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindPictureRepository(
        repository: InMemoryPictureRepository
    ): PictureRepository
}