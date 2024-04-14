package br.com.cattose.app.data.repository

import androidx.paging.PagingSourceFactory
import br.com.cattose.app.data.model.domain.CatDetails
import br.com.cattose.app.data.model.domain.CatImage
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun getCatsPagingFactory(): PagingSourceFactory<Int, CatImage>
    fun getDetails(id: String): Flow<CatDetails>
}
