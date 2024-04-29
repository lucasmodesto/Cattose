package br.com.cattose.app.data.di

import br.com.cattose.app.data.api.CatsApi
import br.com.cattose.app.data.api.KtorCatsApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FlavoredApiModule {

    @Binds
    fun bindProdApi(ktorCatsApi: KtorCatsApi): CatsApi
}
