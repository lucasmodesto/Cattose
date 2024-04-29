package br.com.cattose.app.data.di

import br.com.cattose.app.data.repository.CatRepository
import br.com.cattose.app.data.repository.DefaultCatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindCatRepository(defaultCatRepository: DefaultCatRepository): CatRepository
}
