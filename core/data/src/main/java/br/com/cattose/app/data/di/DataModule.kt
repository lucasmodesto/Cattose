package br.com.cattose.app.data.di

import br.com.cattose.app.data.api.CatsApi
import br.com.cattose.app.data.api.KtorCatsApi
import br.com.cattose.app.data.repository.DefaultCatRepository
import br.com.cattose.app.data.repository.CatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindCatsApi(ktorCatsApi: KtorCatsApi): CatsApi

    @Binds
    abstract fun bindCatRepository(defaultCatRepository: DefaultCatRepository): CatRepository
}
