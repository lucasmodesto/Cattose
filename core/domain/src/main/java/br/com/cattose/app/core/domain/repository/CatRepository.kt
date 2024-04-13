package br.com.cattose.app.core.domain.repository

import br.com.cattose.app.core.domain.model.CatDetails
import br.com.cattose.app.core.domain.model.CatImage
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun getCats(): Flow<List<CatImage>>
    fun getDetails(id: String): Flow<CatDetails>
}
