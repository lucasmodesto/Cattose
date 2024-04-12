package br.com.cattose.app.core.domain.repository

import br.com.cattose.app.core.domain.model.CatDetails
import br.com.cattose.app.core.domain.model.CatImage
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    suspend fun getCats(): Flow<List<CatImage>>
    suspend fun getDetails(id: String): Flow<CatDetails>
}
