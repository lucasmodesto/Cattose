package br.com.cattose.app.data.di

import br.com.cattose.app.data.api.CatsApi
import br.com.cattose.app.data.api.DemoCatsApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface FlavoredApiModule {

    @Binds
    fun bindDemoApi(api: DemoCatsApi): CatsApi
}
