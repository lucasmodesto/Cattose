package br.com.cattose.app.data.repository

import androidx.paging.PagingSourceFactory
import br.com.cattose.app.data.paging.CatsPagingSource
import br.com.cattose.app.data.api.CatsApi
import br.com.cattose.app.data.mapper.toDomain
import br.com.cattose.app.data.model.domain.CatImage
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultCatRepository @Inject constructor(
    private val catsApi: CatsApi
) : CatRepository {
    override fun getCatsPagingFactory(): PagingSourceFactory<Int, CatImage> =
        PagingSourceFactory {
            CatsPagingSource(catsApi)
        }


    override fun getDetails(id: String) = flow {
        emit(catsApi.fetchDetails(id).toDomain())
    }
}
