package br.com.cattose.app.data.repository

import br.com.cattose.app.data.api.CatsApi
import br.com.cattose.app.data.mapper.mapToDomain
import br.com.cattose.app.core.domain.repository.CatRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultCatRepository @Inject constructor(
    private val catsApi: CatsApi
) : CatRepository {

    override fun getCats() = flow {
        emit(catsApi.fetchList().map { it.mapToDomain() })
    }

    override fun getDetails(id: String) = flow {
        emit(catsApi.fetchDetails(id).mapToDomain())
    }
}
